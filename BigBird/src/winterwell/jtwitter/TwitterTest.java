package winterwell.jtwitter;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

//import junit.framework.TestCase;
import winterwell.jtwitter.Twitter.Message;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.Twitter.User;
import winterwell.jtwitter.TwitterException.E403;
//import winterwell.utils.Printer;

/**
 * Unit tests for JTwitter.
 * These only provide partial testing -- sorry.
 * 
 *
 * @author daniel
 */
public class TwitterTest
//extends TestCase // Comment out to remove the JUnit dependency
{

	private static final String TEST_USER = "jtwit";
	private static final String TEST_PASSWORD = "password";

	public static void main(String[] args) {
		TwitterTest tt = new TwitterTest();
		Method[] meths = TwitterTest.class.getMethods();
		for(Method m : meths) {
			if ( ! m.getName().startsWith("test")
					|| m.getParameterTypes().length != 0) continue;
			try {
				m.invoke(tt);
				System.out.println(m.getName());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				System.out.println("TEST FAILED: "+m.getName());
				System.out.println("\t"+e.getCause());
			}
		}
	}

	/**
	 * Check that you can send 160 chars if you wants
	 */
	public void canSend160() {
		String s = "";
		for(int i=0; i<15; i++) {
			s += i+"23456789 ";
		}
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		tw.setStatus(s);
	}

	/**
	 *  NONDETERMINISTIC! Had to increase sleep time to make it more reliable.
	 * @throws InterruptedException
	 */
	public void testDestroyStatus() throws InterruptedException {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		Status s1 = tw.getStatus();
		tw.destroyStatus(s1.getId());
		Status s0 = tw.getStatus();
		assert s0.id != s1.id : "Status id should differ from that of destroyed status";
	}

	public void testDestroyStatusBad() {
		// Check security failure
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		Status hs = tw.getStatus("winterstein");
		try {
			tw.destroyStatus(hs);
			assert false;
		} catch (Exception ex) {
			// OK
		}
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#follow(java.lang.String)}.
	 */
	public void testFollowAndStopFollowing() throws InterruptedException {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<User> friends = tw.getFriends();
		if (Twitter.getUser("winterstein", friends)==null) {
			tw.follow("winterstein");
			Thread.sleep(1000);
		}
		assert tw.isFollowing("winterstein") : friends;

		// Stop
		User h = tw.stopFollowing("winterstein");
		Thread.sleep(1000);
		assert ! tw.isFollowing("winterstein") : friends;

		// break where no friendship exists
		User h2 = tw.stopFollowing("winterstein");
		assert h2==null;

		// Follow
		tw.follow("winterstein");
		Thread.sleep(1000);
		assert tw.isFollowing("winterstein") : friends;

		try {
			User suspended = tw.follow("Alysha6822");
			assert false : "Trying to follow a suspended user should throw an exception";
		} catch (TwitterException e) {
		}
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getFollowerIDs()}
	 * and {@link winterwell.jtwitter.Twitter#getFollowerIDs(String)}.
	 * 
	 */
	public void testFollowerIDs() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<Long> ids = tw.getFollowerIDs();
		for (Long id : ids) {
			// Getting a 403 Forbidden error here - not sure what that means
			// user id = 33036740 is cuasing the problem
			// possibly to do with protected updates?
			try {
				assert tw.isFollower(id.toString(), TEST_USER) : id;
			} catch (E403 e) {
				// this seems to be a corner issue with Twitter's API rather than a bug in JTwitter
				System.out.println(id+" "+e);
			}
		}
		List<Long> ids2 = tw.getFollowerIDs(TEST_USER);
		assert ids.equals(ids2);
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getFriendIDs()}
	 * and {@link winterwell.jtwitter.Twitter#getFriendIDs(String)}.
	 */
	public void testFriendIDs() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<Long> ids = tw.getFriendIDs();
		for (Long id : ids) {
			assert tw.isFollower(TEST_USER, id.toString());
		}
		List<Long> ids2 = tw.getFriendIDs(TEST_USER);
		assert ids.equals(ids2);
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getDirectMessages()}.
	 */
	public void testGetDirectMessages() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<Message> msgs = tw.getDirectMessages();
		for (Message message : msgs) {
			assert message.getRecipient().equals(new User(TEST_USER));
		}
		assert msgs.size() != 0;
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getDirectMessagesSent()}.
	 */
	public void testGetDirectMessagesSent() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<Message> msgs = tw.getDirectMessagesSent();
		for (Message message : msgs) {
			assert message.getSender().equals(new User(TEST_USER));
		}
		assert msgs.size() != 0;
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getFeatured()}.
	 */
	public void testGetFeatured() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<User> f = tw.getFeatured();
		assert f.size() > 0;
		assert f.get(0).status != null;
	}


	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getFollowers()}.
	 */
	public void testGetFollowers() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<User> f = tw.getFollowers();
		assert f.size() > 0;
		assert Twitter.getUser("winterstein", f) != null;
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getFriends()}.
	 */
	public void testGetFriends() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<User> friends = tw.getFriends();
		assert friends != null;
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getFriends(java.lang.String)}.
	 */
	public void testGetFriendsString() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<User> friends = tw.getFriends("winterstein");
		assert friends != null;
	}
	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getFriendsTimeline()}.
	 */
	public void testGetFriendsTimeline() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<Status> ft = tw.getFriendsTimeline();
		assert ft.size() > 0;
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getFriendsTimeline(java.lang.String, java.util.Date)}.
	 */
	public void testGetFriendsTimelineString() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<Status> ft = tw.getFriendsTimeline("winterstein");
		assert ft.size() > 0;
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getPublicTimeline()}.
	 */
	public void testGetPublicTimeline() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<Status> pt = tw.getPublicTimeline();
		assert pt.size() > 5;
	}

	public void testGetRateLimitStats() throws InterruptedException {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		int i = tw.getRateLimitStatus();
		if (i<1) return;
		tw.getStatus();
		Thread.sleep(1000);
		int i2 = tw.getRateLimitStatus();
		assert i - 1 == i2;
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getReplies()}.
	 */
	public void testGetReplies() {
		{
			Matcher m = Status.AT_YOU_SIR.matcher("@dan hello");
			assert m.find();
			m.group(1).equals("dan");
		}
		//		{	// done in code
		//			Matcher m = Status.atYouSir.matcher("dan@email.com hello");
		//			assert ! m.find();
		//		}
		{
			Matcher m = Status.AT_YOU_SIR.matcher("hello @dan");
			assert m.find();
			m.group(1).equals("dan");
		}

		Twitter tw = new Twitter(TEST_USER,TEST_PASSWORD);
		List<Status> r = tw.getReplies();
		for (Status message : r) {
			List<String> ms = message.getMentions();
			assert ms.contains(TEST_USER) : message;
		}
		System.out.println("Replies "+r);
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getStatus(int)}.
	 */
	public void testGetStatus() {
		Twitter tw = new Twitter(TEST_USER,TEST_PASSWORD);
		Status s = tw.getStatus();
		assert s != null;
		System.out.println(s);

		//		// test no status
		//		tw = new Twitter(ANOther Account);
		//		s = tw.getStatus();
		//		assert s == null;
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getStatus(long)}.
	 */
	public void testGetStatusLong() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		Status s = tw.getStatus();
		Status s2 = tw.getStatus(s.getId());
		assert s.text.equals(s2.text) : "Fetching a status by id should yield correct text";
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getUserTimeline()}.
	 */
	public void testGetUserTimeline() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<Status> ut = tw.getUserTimeline();
		assert ut.size() > 0;
	}


	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#getUserTimeline(java.lang.String, java.lang.Integer, java.util.Date)}.
	 */
	public void testGetUserTimelineString() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<Status> ns = tw.getUserTimeline("narrator");
	}


	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#isFollower(String)}
	 * and {@link winterwell.jtwitter.Twitter#isFollower(String, String)}.
	 */
	public void testIsFollower() throws InterruptedException {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		tw.stopFollowing("winterstein");
		Thread.sleep(1000);
		assert ! tw.isFollowing("winterstein");
		tw.follow("winterstein");
		Thread.sleep(1000);
		assert tw.isFollowing("winterstein");
	}

	public void testSearch() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		List<Status> javaTweets = tw.search("java");
		assert javaTweets.size() != 0;
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#sendMessage(java.lang.String, java.lang.String)}.
	 */
	public void testSendMessage() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		Message sent = tw.sendMessage("winterstein", "Please ignore this message");
		System.out.println(""+sent);
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#show(java.lang.String)}.
	 */
	public void testShow() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		User show = tw.show(TEST_USER);
		assert show != null;

		// a protected user
		User ts = tw.show("tassosstevens");
	}

	/**
	 * Test method for {@link winterwell.jtwitter.Twitter#updateStatus(java.lang.String)}.
	 */
	public void testUpdateStatus() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		String s = "Experimenting (http://winterwell.com at "+new Date().toString()+")";
		Status s2a = tw.updateStatus(s);
		Status s2b = tw.getStatus();
		assert s2b.text.equals(s) : s2b.text;
		assert s2a.id == s2b.id;
		//		assert s2b.source.equals("web") : s2b.source;
	}


	public void testUserExists() {
		Twitter tw = new Twitter(TEST_USER, TEST_PASSWORD);
		assert tw.userExists("spoonmcguffin") : "There is a Spoon, honest";
		assert ! tw.userExists("chopstickmcguffin") : "However, there is no Chopstick";
		assert ! tw.userExists("Alysha6822") : "Suspended users show up as nonexistent";
	}

}
