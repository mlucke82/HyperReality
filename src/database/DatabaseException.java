package database;

@SuppressWarnings("serial")
public class DatabaseException extends Exception {

  public  DatabaseException() {
    super();
  }
  
  public DatabaseException(String s) {
    super(s);
  }
}
