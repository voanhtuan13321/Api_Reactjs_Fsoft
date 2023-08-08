package fa.edu.api.common;

/**
 * Query string class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 08/08/2023
 */
public class QueryString {
  public static final String FIND_ALL_SEARCH
      = "SELECT * FROM tb_book "
      + "WHERE CONCAT(title, '-', description, '-', author) LIKE %?1%";

  public static final String FIND_ALL_BY_CATEGORY_SEARCH
      = "SELECT * FROM tb_book "
      + "WHERE category_id = ?1 AND CONCAT(title, '-', description, '-', author) LIKE %?2%";

  private QueryString() { }
}
