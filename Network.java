/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        if (name == null) {
            return null; 
        }
        for (int i = 0; i < userCount; i++) {
            if (users[i] != null && users[i].getName().equalsIgnoreCase(name)) {
                return users[i];
            }
        }
        return null; 
    }
    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */

    public boolean addUser(String name) {
        if (userCount == users.length) {  // Check if the array is full
            return false;
        }
        if (name == null || getUser(name) != null) {  // Check for null or duplicate names
            return false;
        }
        users[userCount] = new User(name);  // Add user
        userCount++;
        return true;
    }
    

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (name1 == null || name2 == null) {
            return false; 
        }
        if (name1.equals(name2)) {
            return false; 
        }
        User user1 = getUser(name1);
        User user2 = getUser(name2);
        if (user1 == null || user2 == null) {
            return false;
        }
        return user1.addFollowee(name2);
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User user = getUser(name);
        if (user == null) return null;
        int max = 0; 
        String maxName = null; 
        for (int i = 0; i < userCount; i++) {
            User candidate = users[i];
            if (candidate == null || candidate.getName().equals(name)) continue;
            int mutualFollowees = user.countMutual(candidate); 
            if (mutualFollowees > max) {
                max = mutualFollowees;
                maxName = candidate.getName();
            }
        }
    
        return maxName; // Return the most recommended user to follow, or null
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        if (userCount == 0) {
            return null;
        }
        int maxFollowers = 0;
        String mostPopular = null;
        for (int i = 0; i < userCount; i++) {
            String currentUser = users[i].getName();
            int followerCount = 0;

            for (int j = 0; j < userCount; j++) {
                if (users[j].follows(currentUser)) {
                    followerCount++;
                }
            }
            if (followerCount > maxFollowers) {
                maxFollowers = followerCount;
                mostPopular = currentUser;
            }
        }
        return mostPopular;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
            int c=0;
                for(int j = 0; j<userCount; j++)
                {
                    if(getUser(name).getName().equals(users[j].getName())) continue;
                    if(users[j].follows(name))
                    c++;
                }
                return c;
    }


    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String result = "Network:";
        for (int i = 0; i < userCount; i++) {
            if (users[i] != null) {
                result += "\n" + users[i].toString();
            }
        }
        return result;
    }
 }

