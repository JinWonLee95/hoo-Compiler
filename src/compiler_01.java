import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class compiler_01 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// �����
		String result = "";

		// FileReader�� ���ڷ� �ϴ� BufferedReader ��ü ����
		// hoo����, c���� ��� : C:\Users\JinWon\eclipse-workspace\Compiler_01
		BufferedReader br = new BufferedReader(new FileReader("test.hoo"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("test.c"));
		

		bw.write("#include <stdio.h>\n\nint main(){\n\n");

		// ������ �� ���徿 �о�´�.
		String str = br.readLine();

		// EOF�� null���ڸ� �����ϰ� �ִ�.
		while (str != null) {

			// : �պκ� ������ ����
			String[] colon_split = str.split(":");

			// �պκ� ���� �� string�� ���� �κ��� ��������
			String[] bracket_split = colon_split[0].split("]");

			String content = "";
			
			// ������ ��� \n���� ��ü, �ƴ� ��� content�� ����
			if (bracket_split[0].length() < 2) {
				content = "\\n";
			} else {
				content += bracket_split[0].substring(1, bracket_split[0].length());
			}

			/* �պκ� ��� */

			// �ɼ��� ���� ���
			if (bracket_split.length >= 2) {
				// ġȯ �ɼ��� ���
				if (bracket_split[1].length() > 2) {
					// �ٲ� char�� �ٲ� char�� �и�, ����
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

			/* �޺κ� ������ */

			// Ű���尡 ���� ���
			if (colon_split.length > 1 && !(colon_split[1].equals(" "))) {
				// Ű���忡 �ݺ��� ���� ���
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

					// �ݺ� �ڿ� ignore�̳� print�� �ִ� ���
					if (colon_split[1].contains("print")) {
						bw.write("	printf(\"%s\", \"" + result + "\");\n");
						result = "";
					}
				} else {
					// �ݺ��� ���� �ٷ� print�� ������ ���
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
			// ���� ���ڿ��� �������ش�.
			str = br.readLine();
		}

		// ���� ���ڿ��� ����Ѵ�.
		System.out.println(result);

		bw.write("	return 0;\n}");

		// FileReader�ʹ� �ٸ��� ��� �� �� �ݾ��־�� �Ѵ�.
		br.close();
		bw.close();
	}

}
