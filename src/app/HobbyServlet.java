package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/hobby")
public class HobbyServlet extends HttpServlet {

	/********************************************************************************
	 * 以下のdoGet/doPostを実装して下さい。
	 * importなどは自由に行って下さい。
	 ********************************************************************************/

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 必須機能「趣味参照機能」
		String syainId = "0001";
		// JDBCドライバの準備
				try {

				    // JDBCドライバのロード
				    Class.forName("oracle.jdbc.driver.OracleDriver");

				} catch (ClassNotFoundException e) {
				    // ドライバが設定されていない場合はエラーになります
				    throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
				}

				// データベースにアクセスするために、データベースのURLとユーザ名とパスワードを指定
				String url = "jdbc:oracle:thin:@localhost:1521:XE";
				String user = "wt2";
				String pass = "wt2";

				// 実行するSQL文
				String sql = "select" +
						"SYAINID," +
						"HOBBY_ID," +
						"CATEGORY_ID," +
						"CATEGORY_NAME," +
						"HOBBY_NAME" +
						"from" +
						"MS_SYAIN," +
						"MS_SYAIN_HOBBY," +
						"MS_HOBBY," +
						"MS_CATEGORY" +
						"where 1=1" +
						"and SYAINID = SYAINID" +
						"and HOBBY_ID = HOBBY_ID" +
						"and CATEGORY_ID = CATEGORY_ID" +
						"and SYAINID = '"+syainId+"'";


				// 趣味リスト（Hobby型のリスト）
				List<Hobby> list = new ArrayList<>();

				// エラーが発生するかもしれない処理はtry-catchで囲みます
				// この場合はDBサーバへの接続に失敗する可能性があります
				try (
						// データベースへ接続します
						Connection con = DriverManager.getConnection(url, user, pass);

						// SQLの命令文を実行するための準備をおこないます
						Statement stmt = con.createStatement();

						// SQLの命令文を実行し、その結果をResultSet型のrsに代入します
						ResultSet rs1 = stmt.executeQuery(sql);) {
					// SQL実行後の処理内容

					// SQL実行結果を商品リストに追加していく。
					while (rs1.next()) {
						// 一つ分の成績情報を入れるためReSScordインスタンスを生成
						Hobby hobby = new Hobby();
						// SQLの取得結果をインスタンスに代入
						hobby.setHobby(rs1.getString("HOBBY_NAME"));
						hobby.setHobbyCategory(rs1.getString("CATEGORY_NAME"));

						// 値を格納したHobbyインスタンスをリストに追加
						list.add(hobby);
					}
				} catch (Exception e) {
					throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。詳細：[%s]", e.getMessage()), e);
				}

				// アクセスした人に応答するためのJSONを用意する
				PrintWriter pw = response.getWriter();
				// JSONで出力する
				pw.append(new ObjectMapper().writeValueAsString(list));

		// -- ここまで --
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		// TODO 任意機能「趣味投稿機能に挑戦する場合はこちらを利用して下さい」

		// -- ここまで --
	}

}
