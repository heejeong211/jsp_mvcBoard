package edu.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import edu.bit.dto.BDto;

public class BDao {
	DataSource dataSource; // 커넥션 풀은 언제 가져오면 좋을까? 객체생성될 때 생성자에 넣어주면 됨.
	
	public BDao() {
		try {
			Context context = new InitialContext(); // server에서 context.xml에 있는 정보를 가져와서 메모리에 올리는 것.
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(String bName, String bTitle, String bContent) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, 0, mvc_board_seq.currval, 0, 0 )";
			// mvc_board_seq.nextval 오라클에서만 있음. my-sql은 없음. sequence가 가지고 있는 글번호 숫자를 글이 추가 될 때 마다 자동으로 증가시켜 줌. mvc_board_seq.currval 현재 value. 컬럼명과 values는 순서 맞춰 줘야 함.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn == 1 ) { // 디버깅용
				System.out.println("정상");
			} else {
				System.out.println("비정상 처리");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public ArrayList<BDto> list() { // 이거 외우자!!
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select * from mvc_board order by bGroup desc, bStep asc"; // 시간 순으로 게시글, 댓글 나열
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				
				Timestamp bDate = resultSet.getTimestamp("bDate"); // Date 주의!!!
				
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				dtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dtos;
	}

	public BDto contentView(String strID) {
		
		// upHit 호출
		upHit(strID);
		
		BDto dto = null; // dto가 게시판 줄 하나
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select * from mvc_board where bId = ?";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.parseInt(strID)); // 문자를 숫자로 바꿔주는
		
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				
				Timestamp bDate = resultSet.getTimestamp("bDate"); // Date 주의!!!
				
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dto;
	}

	private void upHit(String strID) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "update mvc_board set bHit = bHit + 1 where bId = ?";
			// mvc_board_seq.nextval 오라클에서만 있음. my-sql은 없음. sequence가 가지고 있는 글번호 숫자를 글이 추가 될 때 마다 자동으로 증가시켜 줌. mvc_board_seq.currval 현재 value. 컬럼명과 values는 순서 맞춰 줘야 함.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, strID);

			int rn = preparedStatement.executeUpdate();
			
			if(rn == 1 ) { // 디버깅용
				System.out.println("정상");
			} else {
				System.out.println("비정상 처리");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	public void modify(String strID, String strName, String strTitle, String strContent) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		
		try {
			connection = dataSource.getConnection();
			
			String query = "update mvc_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, strName);
			preparedStatement.setString(2, strTitle);
			preparedStatement.setString(3, strContent);
			preparedStatement.setInt(4, Integer.parseInt(strID));
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn == 1 ) { // 디버깅용
				System.out.println("정상");
			} else {
				System.out.println("비정상 처리");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void delete(String strID) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "delete from mvc_board where bId = ?";
			
			preparedStatement = connection.prepareStatement(query);
		
			preparedStatement.setInt(1, Integer.parseInt(strID));
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn == 1 ) { // 디버깅용
				System.out.println("정상");
			} else {
				System.out.println("비정상 처리");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	public BDto reply_view(String strID) {
		
		BDto dto = null; // dto가 게시판 줄 하나
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select * from mvc_board where bId = ?";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.parseInt(strID)); // 문자를 숫자로 바꿔주는
		
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				
				Timestamp bDate = resultSet.getTimestamp("bDate"); // Date 주의!!!
				
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return dto;
	}

	public void reply(String bId, String bName, String bTitle, String bContent, String bGroup, String bStep,
			String bIndent) {
		
		// 최신댓글이 위로 올라와서 DB에서 group과 step, indent가 이상해짐... 지금은 에러 안나도 나중에 날 수 있음.
		replyShape(bGroup, bStep); // 여기에서 step과 뒤에 나오는 replyShape 함수의 step과 값이 다름. 지금 step은 0임. 왜냐하면 원본글은 step이 0이기 때문에
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
			// mvc_board_seq.nextval 오라클에서만 있음. my-sql은 없음. sequence가 가지고 있는 글번호 숫자를 글이 추가 될 때 마다 자동으로 증가시켜 줌. 컬럼명과 values는 순서 맞춰 줘야 함.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			
			preparedStatement.setInt(4, Integer.parseInt(bGroup));
			preparedStatement.setInt(5, Integer.parseInt(bStep) + 1);
			preparedStatement.setInt(6, Integer.parseInt(bIndent) + 1);
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn == 1 ) { // 디버깅용
				System.out.println("정상");
			} else {
				System.out.println("비정상 처리");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	
	// private여도 상관없음. 여기서만 쓰기 때문에
	private void replyShape(String bGroup, String bStep) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			// 같은 그룹 넘버에 스탭이 자기꺼 보다 큰 친구한테 적용하는 댓글 관련 쿼리문
			String query = "update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
			// mvc_board_seq.nextval 오라클에서만 있음. my-sql은 없음. sequence가 가지고 있는 글번호 숫자를 글이 추가 될 때 마다 자동으로 증가시켜 줌. mvc_board_seq.currval 현재 value. 컬럼명과 values는 순서 맞춰 줘야 함.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.parseInt(bGroup));
			preparedStatement.setInt(2, Integer.parseInt(bStep));
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn == 1 ) { // 디버깅용
				System.out.println("정상");
			} else {
				System.out.println("비정상 처리");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
