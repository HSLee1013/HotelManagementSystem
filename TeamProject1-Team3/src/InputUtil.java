import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputUtil {

	private String buffer;
	private Scanner scanner;
	private String invalidInputPhrase;
	private String dateTimeFormat;

	/**
	 * 생성자 스캐너 입력
	 */
	public InputUtil(Scanner scanner) {
		super();
		this.scanner = scanner;
		invalidInputPhrase = "[WRONG INPUT]";
		dateTimeFormat = "yyyyMMdd";
	}

	/**
	 * 생성자 스캐너+유효하지 않은 입력 문구 커스텀
	 */
	public InputUtil(Scanner scanner, String invalidInputPhrase) {
		super();
		this.scanner = scanner;
		this.invalidInputPhrase = invalidInputPhrase;
		dateTimeFormat = "yyyyMMdd";
	}

	/**
	 * 유효하지 않은 입력 문구 설정
	 */
	public void setInvalidInputPhrase(String invalidInputPhrase) {
		this.invalidInputPhrase = invalidInputPhrase;
	}

	/**
	 * 입력받을 날짜 형식 설정
	 */
	public void setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	/**
	 * 버퍼에 한 줄을 양옆 공백을 제거하고 입력
	 */
	public void inputTrimedBuffer() {
		buffer = scanner.nextLine().trim();
	}

	/**
	 * 버퍼에 한 줄을 입력
	 */
	public void inputBuffer() {
		buffer = scanner.nextLine();
	}

	/**
	 * 버퍼를 문자열로 반환
	 */
	public String getBufferToString() {
		return buffer;
	}

	/**
	 * 버퍼를 정수로 반환
	 */
	public int getBufferToInt() {
		return Integer.parseInt(buffer);
	}

	/**
	 * 버퍼를 실수로 반환
	 */
	public double getBufferToDouble() {
		return Double.parseDouble(buffer);
	}

	/**
	 * 이름 형식으로 입력될 때까지 입력 받음
	 */
	public String inputName() {
		while (true) {
			inputTrimedBuffer();
			if (isName())
				return buffer;
			System.out.println(invalidInputPhrase);
		}
	}

	/**
	 * 전화번호 형식으로 입력될 때까지 입력 받음
	 */
	public String inputPhonNumber() {
		while (true) {
			inputTrimedBuffer();
			if (isPhoneNumber())
				return buffer;
			System.out.println(invalidInputPhrase);
		}
	}

	/**
	 * 날짜 형식으로 입력될 때까지 입력 받음
	 */
	public String inputDate() {
		while (true) {
			inputTrimedBuffer();
			if (isDate())
				return buffer;
			System.out.println(invalidInputPhrase);
		}
	}

	/**
	 * 정수가 입력될 때까지 입력 받음
	 */
	public int inputInt() {
		while (true) {
			inputTrimedBuffer();
			if (isInt())
				return getBufferToInt();
			System.out.println(invalidInputPhrase);
		}
	}

	/**
	 * start이상 end이하의 정수가 입력될 때까지 입력 받음
	 */
	public int inputIntInRange(int start, int end) {
		while (true) {
			int input = inputInt();
			if (isInRange(input, start, end))
				return input;
			System.out.println(invalidInputPhrase);
		}
	}

	/**
	 * n이 start이상 end이하면 참 아니면 거짓
	 */
	private boolean isInRange(int n, int start, int end) {
		return start <= n && n <= end;
	}

	/**
	 * selects배열 안의 정수가 입력될 때까지 입력 받음
	 */
	public int inputIntInSelects(int... selects) {
		while (true) {
			int input = inputInt();
			if (isInSelects(input, selects))
				return input;
			System.out.println(invalidInputPhrase);
		}
	}

	/**
	 * n이 selects안에 있으면 참 아니면 거짓
	 */
	private boolean isInSelects(int n, int[] selects) {
		for (int s : selects) {
			if (s == n)
				return true;
		}
		return false;
	}

	/**
	 * n이 start이상 end이하거나 selects배열 안의 정수가 입력될 때까지 입력 받음
	 */
	public int inputIntInRangeOrSelects(int start, int end, int... selects) {
		while (true) {
			int input = inputInt();
			if (isInSelects(input, selects))
				return input;
			if (isInRange(input, start, end))
				return input;
			System.out.println(invalidInputPhrase);
		}
	}

	/**
	 * 실수가 입력될 때까지 입력 받음
	 */
	public double inputDouble() {
		while (true) {
			inputTrimedBuffer();
			if (isDouble())
				return getBufferToDouble();
			System.out.println(invalidInputPhrase);
		}
	}

	/**
	 * 버퍼가 정수라면 참, 아니면 거짓
	 */
	private boolean isInt() {
		try {
			Integer.parseInt(buffer);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * 버퍼가 실수라면 참, 아니면 거짓
	 */
	private boolean isDouble() {
		try {
			Double.parseDouble(buffer);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * 버퍼가 이름 형식이라면 참, 아니면 거짓
	 */
	private boolean isName() {
		String pattern = "^[a-zA-Z가-힣].*$";
		return Pattern.matches(pattern, buffer);
	}

	/**
	 * 버퍼가 전화번호 형식이라면 참, 아니면 거짓
	 */
	private boolean isPhoneNumber() {
		String pattern = "^\\d{3}-\\d{3,4}-\\d{4}$";
		return Pattern.matches(pattern, buffer);
	}

	/**
	 * 버퍼가 날짜 형식이라면 참, 아니면 거짓
	 */
	private boolean isDate() {
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
			LocalDate.parse(buffer, dateTimeFormatter);
			return true;
		} catch (DateTimeParseException ex) {
			return false;
		}
	}

}
