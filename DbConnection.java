import java.sql.*;

public class DbConnection {
    private static Connection con = null;
    private static PreparedStatement st = null;
    private static ResultSet rs = null;
    private static String username = "root";
    private static String password = "pass";
    private static String URL = "jdbc:mysql://localhost:3306";
    private static final String QUERY_USE_DB = "use db_name";
    private static String QUERY_SELECT_TABLE = "SELECT * FROM users WHERE SALARY_MIN >= ?";
    private static final float SALARY_MIN = 3500;
    private static final int POSITION_SALARY = 1;

    public static void main(String[] args) {
        try  {
            //Загружаем драйвер
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            // создаем соединение
            con = DriverManager.getConnection(URL, username, password);
            if (con != null) {
                System.out.println("Connection Successful !\n");
            }
            else {
                System.out.println("Connection NOT Successful !\n");
                return;
            }
            // запрос на использование БД 'laz"
            st = con.prepareStatement(QUERY_USE_DB);
            st.execute();
            // запрос выбор из таблицы с условием, что З/П SALARY_MIN и больше
            st = con.prepareStatement(QUERY_SELECT_TABLE);
            st.setFloat(POSITION_SALARY, SALARY_MIN);
            rs = st.executeQuery();
            // получаю количество столбцов
            int x = rs.getMetaData().getColumnCount();
            for (int i = 1; i<= x; i++){
                System.out.print("|"+ rs.getMetaData().getColumnName(i));
            }
            System.out.println();
            //вывожу результирующую таблицу
            while(rs.next()){
                for(int i = 1; i <= x; i++){
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Not Successful :(");
        }
        finally {
                try {
                    if (rs != null)
                        rs.close();
                    if (st != null)
                        st.close();
                    if (con != null)
                        con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }
}
