package fa.edu.api.common;

/**
 * Query string class.
 *
 * @author AnhTuan
 * @version 1.0
 * @since 08/08/2023
 */
public class QueryString {

  private QueryString() { }

  public  static final String FIND_ALL_SEARCH
      = "SELECT * "
      + "FROM tb_book "
      + "WHERE CONCAT(title, '-', description, '-', author) LIKE %:search% "
      + "ORDER BY book_id "
      + "OFFSET :numRowInPage * (:numPage - 1) ROWS "
      + "FETCH NEXT :numRowInPage ROWS ONLY";

  public static final String COUNT_ALL_BOOK
      = "SELECT COUNT(1) "
      + "FROM tb_book "
      + "WHERE CONCAT(title, '-', description, '-', author) LIKE %:search% ";

  public static final String COUNT_ALL_BOOK_BY_CATEGORY
      = "SELECT COUNT(1) "
      + "FROM tb_book "
      + "WHERE CONCAT(title, '-', description, '-', author) LIKE %:search% AND category_id = :category_id";

  public static final String FIND_ALL_BY_CATEGORY_SEARCH
      = "SELECT * "
      + "FROM tb_book "
      + "WHERE CONCAT(title, '-', description, '-', author) LIKE %:search% AND category_id = :category_id";

  public static final String TOP_BEST_SELLING_BOOK
      = "SELECT DISTINCT TOP (?1) book_id, SUM(quantity) AS 'quantity' "
      + "FROM tb_order_detail "
      + "GROUP BY book_id "
      + "ORDER BY quantity DESC";

  public static final String TOP_USER_BUY_THE_MOST
      = "SELECT TOP(?1) od.user_id, SUM(dt.quantity) as quantity "
      + "FROM tb_order od "
      + "INNER JOIN tb_order_detail dt ON dt.order_id = od.id "
      + "INNER JOIN tb_user us ON us.user_id = od.user_id "
      + "GROUP BY od.user_id "
      + "ORDER BY quantity DESC";

  public static final String STATISTICAL
      = "SELECT od.order_date, dt.quantity * b.price AS total "
      + "FROM tb_order od "
      + "INNER JOIN tb_order_detail dt ON dt.order_id = od.id "
      + "INNER JOIN tb_book b ON b.book_id = dt.book_id "
      + "WHERE od.is_confirm = 1 AND year(od.order_date) = :year AND month(od.order_date) = :date";

  public static final String STATISTICAL_YEAR
      = "SELECT SUM(dt.quantity * b.price) AS total "
      + "FROM tb_order od "
      + "INNER JOIN tb_order_detail dt ON dt.order_id = od.id "
      + "INNER JOIN tb_book b ON b.book_id = dt.book_id "
      + "WHERE od.is_confirm = 1 AND year(od.order_date) = :year";
}
