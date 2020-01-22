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
	DataSource dataSource; // Ŀ�ؼ� Ǯ�� ���� �������� ������? ��ü������ �� �����ڿ� �־��ָ� ��.
	
	public BDao() {
		try {
			Context context = new InitialContext(); // server���� context.xml�� �ִ� ������ �����ͼ� �޸𸮿� �ø��� ��.
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
			// mvc_board_seq.nextval ����Ŭ������ ����. my-sql�� ����. sequence�� ������ �ִ� �۹�ȣ ���ڸ� ���� �߰� �� �� ���� �ڵ����� �������� ��. mvc_board_seq.currval ���� value. �÷���� values�� ���� ���� ��� ��.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn == 1 ) { // ������
				System.out.println("����");
			} else {
				System.out.println("������ ó��");
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

	public ArrayList<BDto> list() { // �̰� �ܿ���!!
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select * from mvc_board order by bGroup desc, bStep asc"; // �ð� ������ �Խñ�, ��� ����
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				
				Timestamp bDate = resultSet.getTimestamp("bDate"); // Date ����!!!
				
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
		
		// upHit ȣ��
		upHit(strID);
		
		BDto dto = null; // dto�� �Խ��� �� �ϳ�
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select * from mvc_board where bId = ?";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.parseInt(strID)); // ���ڸ� ���ڷ� �ٲ��ִ�
		
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				
				Timestamp bDate = resultSet.getTimestamp("bDate"); // Date ����!!!
				
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
			// mvc_board_seq.nextval ����Ŭ������ ����. my-sql�� ����. sequence�� ������ �ִ� �۹�ȣ ���ڸ� ���� �߰� �� �� ���� �ڵ����� �������� ��. mvc_board_seq.currval ���� value. �÷���� values�� ���� ���� ��� ��.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, strID);

			int rn = preparedStatement.executeUpdate();
			
			if(rn == 1 ) { // ������
				System.out.println("����");
			} else {
				System.out.println("������ ó��");
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
			
			if(rn == 1 ) { // ������
				System.out.println("����");
			} else {
				System.out.println("������ ó��");
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
			
			if(rn == 1 ) { // ������
				System.out.println("����");
			} else {
				System.out.println("������ ó��");
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
		
		BDto dto = null; // dto�� �Խ��� �� �ϳ�
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "select * from mvc_board where bId = ?";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.parseInt(strID)); // ���ڸ� ���ڷ� �ٲ��ִ�
		
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				
				Timestamp bDate = resultSet.getTimestamp("bDate"); // Date ����!!!
				
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
		
		// �ֽŴ���� ���� �ö�ͼ� DB���� group�� step, indent�� �̻�����... ������ ���� �ȳ��� ���߿� �� �� ����.
		replyShape(bGroup, bStep); // ���⿡�� step�� �ڿ� ������ replyShape �Լ��� step�� ���� �ٸ�. ���� step�� 0��. �ֳ��ϸ� �������� step�� 0�̱� ������
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String query = "insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
			// mvc_board_seq.nextval ����Ŭ������ ����. my-sql�� ����. sequence�� ������ �ִ� �۹�ȣ ���ڸ� ���� �߰� �� �� ���� �ڵ����� �������� ��. �÷���� values�� ���� ���� ��� ��.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			
			preparedStatement.setInt(4, Integer.parseInt(bGroup));
			preparedStatement.setInt(5, Integer.parseInt(bStep) + 1);
			preparedStatement.setInt(6, Integer.parseInt(bIndent) + 1);
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn == 1 ) { // ������
				System.out.println("����");
			} else {
				System.out.println("������ ó��");
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
	
	// private���� �������. ���⼭�� ���� ������
	private void replyShape(String bGroup, String bStep) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = dataSource.getConnection();
			
			// ���� �׷� �ѹ��� ������ �ڱⲨ ���� ū ģ������ �����ϴ� ��� ���� ������
			String query = "update mvc_board set bStep = bStep + 1 where bGroup = ? and bStep > ?";
			// mvc_board_seq.nextval ����Ŭ������ ����. my-sql�� ����. sequence�� ������ �ִ� �۹�ȣ ���ڸ� ���� �߰� �� �� ���� �ڵ����� �������� ��. mvc_board_seq.currval ���� value. �÷���� values�� ���� ���� ��� ��.
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.parseInt(bGroup));
			preparedStatement.setInt(2, Integer.parseInt(bStep));
			
			int rn = preparedStatement.executeUpdate();
			
			if(rn == 1 ) { // ������
				System.out.println("����");
			} else {
				System.out.println("������ ó��");
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
