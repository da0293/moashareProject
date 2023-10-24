package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public class InsertDummyData {
    public static void main(String[] args) {
      String jdbcUrl = "jdbc:mysql://localhost:3306/da_db?serverTimezone=UTC&characterEncoding=UTF-8";
      String username = "root";
     String password = "12345678";

       // 더미 데이터 생성 및 삽입(멤버)
//       try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
//          String insertQuery = "INSERT INTO member (email, password, nickname, auth, provider, join_dt) VALUES (?, ?, ?, ?, ?, NOW())";
//            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
//
//            // 반복문을 사용하여 더미 데이터 삽입
//            for (int i = 1; i <= 10000; i++) {
//                preparedStatement.setString(1, "user" + i + "@ex3.com");
//                preparedStatement.setString(2, "password" + i);
//               preparedStatement.setString(3, "user2" + i);
//                preparedStatement.setString(4, "ROLE_USER");
//                preparedStatement.setString(5, "");
//              preparedStatement.executeUpdate();
//       }
//            
//           System.out.println("더미 데이터 삽입 완료");
//       } catch (SQLException e) {
//           e.printStackTrace();
//    }
//  }
     //board테이블에 데이터 insert
       try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
       	String insertQuery = "INSERT INTO Board (title, content, hits, member_id, replycnt, reg_dt) VALUES (?, ?, ?, ?, ?, ?)";
       	String deleteQuery="delete from Board where id=?";
          PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

       // Calendar 객체를 생성하고 원하는 날짜(10월 1일)를 설정합니다.
          Calendar calendar = Calendar.getInstance();
          calendar.set(Calendar.YEAR, 2023); // 원하는 연도
          calendar.set(Calendar.MONTH, Calendar.OCTOBER); // 10월
          calendar.set(Calendar.DAY_OF_MONTH, 1); // 1일

          // Calendar에서 Timestamp로 변환합니다.
          Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

          // PreparedStatement에 설정합니다.
          preparedStatement.setTimestamp(6, timestamp);
            for (int i = 1; i <= 10000; i++) {
               preparedStatement.setString(1, "Title3 " + i);
               preparedStatement.setString(2, "Content " + i);
               preparedStatement.setInt(3, i * 1); // Example hits value
               preparedStatement.setLong(4, 1); // Assuming member_id values from 1 to 10000
                preparedStatement.setInt(5, 0); // Example replycnt value
               preparedStatement.setTimestamp(6, timestamp); // Current timestamp

              preparedStatement.executeUpdate();
            }
//         for( int i=51000; i<=70999; i++) {
//          	preparedStatement.setLong(1, i); // Assuming member_id values from 1 to 100004
//            	preparedStatement.executeUpdate();
//           }

            System.out.println("더미 데이터 삽입 완료");
        } catch (SQLException e) {
           e.printStackTrace();
       }
   }
}
//위의 코드 예제에서 INSERT INTO member 쿼리를 사용하여 member 테이블에 더미 데이터를 삽입하고 있습니다. 반복문을 사용하여 1부터 10,000까지의 더미 데이터를 생성하고 삽입합니다. 데이터베이스 연결 정보를 jdbcUrl, username, password 변수에 설정해야 합니다. 또한, MySQL JDBC 드라이버를 클래스패스에 추가해야 합니다.
//
//코드를 실행하면 더미 데이터가 MySQL 데이터베이스에 삽입됩니다.더미 데이터를 삽입하는 양에 따라 실행 시간이 다를 수 있으므로 참고하세요.






