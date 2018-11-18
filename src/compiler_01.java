import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class compiler_01 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// 결과물
		String result = "";

		// FileReader를 인자로 하는 BufferedReader 객체 생성
		// hoo파일, c파일 경로 : C:\Users\JinWon\eclipse-workspace\Compiler_01
		BufferedReader br = new BufferedReader(new FileReader("test.hoo"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("test.c"));
		

		bw.write("#include <stdio.h>\n\nint main(){\n\n");

		// 파일을 한 문장씩 읽어온다.
		String str = br.readLine();

		// EOF는 null문자를 포함하고 있다.
		while (str != null) {

			// : 앞부분 내용을 저장
			String[] colon_split = str.split(":");

			// 앞부분 내용 중 string과 조건 부분을 나누어줌
			String[] bracket_split = colon_split[0].split("]");

			String content = "";
			
			// 공백일 경우 \n으로 대체, 아닐 경우 content에 저장
			if (bracket_split[0].length() < 2) {
				content = "\\n";
			} else {
				content += bracket_split[0].substring(1, bracket_split[0].length());
			}

			/* 앞부분 경우 */

			// 옵션이 있을 경우
			if (bracket_split.length >= 2) {
				// 치환 옵션일 경우
				if (bracket_split[1].length() > 2) {
					// 바꿀 char와 바뀔 char를 분리, 저장
					String[] exchange = bracket_split[1].split("/");

					if (content.contains(exchange[1])) {
						content = content.replace(exchange[1].charAt(0), exchange[0].charAt(1));
					}
				} else if (bracket_split[1].charAt(1) == 'U') {
					content = content.toUpperCase();
				} else if (bracket_split[1].charAt(1) == 'L') {
					content = content.toLowerCase();
				}
			} else {
				// result += content;
			}

			/* 뒷부분 나누기 */

			// 키워드가 있을 경우
			if (colon_split.length > 1 && !(colon_split[1].equals(" "))) {
				// 키워드에 반복이 있을 경우
				if (colon_split[1].contains(")")) {
					String[] recursive = colon_split[1].split("\\)");

					String temp = recursive[0].substring(recursive[0].lastIndexOf("(") + 1);
					int times = Integer.parseInt(temp);

					String temp2 = "";
					result += content;

					while (times != 0) {
						temp2 += result;
						times--;
					}
					result = temp2;

					// 반복 뒤에 ignore이나 print가 있는 경우
					if (colon_split[1].contains("print")) {
						bw.write("	printf(\"%s\", \"" + result + "\");\n");
						result = "";
					}
				} else {
					// 반복이 없고 바로 print가 나오는 경우
					if (colon_split[1].contains("print")) {
						result += content;
						bw.write("	printf(\"%s\", \"" + result + "\");\n");
						result = "";
					}
				}
				if (colon_split[1].contains("ignore")) {
					result = "";
				}
			} else {
				result += content;
			}
			// 다음 문자열을 가르켜준다.
			str = br.readLine();
		}

		// 읽은 문자열을 출력한다.
		System.out.println(result);

		bw.write("	return 0;\n}");

		// FileReader와는 다르게 사용 후 꼭 닫아주어야 한다.
		br.close();
		bw.close();
	}

}
