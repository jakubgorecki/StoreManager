
package ia.storemanager;

/**
 * 
 * Responsible for handling a notification displayed in the table on the main window.
 */

public class MainWindowNotification {
    private String issue;
    private String name;

    /**
     * Creates an individual notification with a name of a product and its issue.
     * @param issue
     * @param name 
     */
    
    public MainWindowNotification(String issue, String name) {
        this.issue = issue;
        this.name = name;
    }

    
    /**
     * 
     * @return The product's issue.
     */
    
    public String getIssue() {
        return issue;
    }

    
    /**
     * 
     * @param issue A product's issue.
     */
    
    public void setIssue(String issue) {
        this.issue = issue;
    }

    
    /**
     * 
     * @return The product's name.
     */
    
    public String getName() {
        return name;
    }

    
    /**
     * 
     * @param name A product's name.
     */
    
    public void setName(String name) {
        this.name = name;
    }
    
}
