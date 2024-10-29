public class Room {
	enum Cleaning {
		COMPLETE, NEED, INSPECTION, REMODELING;
	}

	enum State {
		EMPTY, RESERVED, OCCUPIED, CLOSED;
	}

	enum Type {
		STANDARD1, STANDARD2, SUPERIOR1, SUPERIOR2, DELUXE1, DELUXE2, EXECUTIVE1, EXECUTIVE2, SUITE;
	}

	private int roomNum;
	private State roomState; // 0 : 빈방 1 : 예약 2 : 투숙
	private Customer customer;
	private Customer reservationCustomer;
	private Customer prevCustomer;
	private Type roomType; // 스탠다드 1,2 | 슈페리어 1,2 | 디럭스 1,2 | 이그젝큐티브 1,2 | 스위트
	private int guestNum;
	private boolean useExtraBed;
	private Cleaning cleaningState;

	public Room(int roomNum) { // 생성자
		super();
		this.roomNum = roomNum;
		roomState = State.EMPTY;
		cleaningState = Cleaning.COMPLETE;
	}

	public Room(int roomNum, Type roomType) {
		super();
		this.roomNum = roomNum;
		this.roomType = roomType;
		roomState = State.EMPTY;
		cleaningState = Cleaning.COMPLETE;
	}

	public Customer getPrevCustomer() {
		return prevCustomer;
	}

	public void setPrevCustomer(Customer prevCustomer) {
		this.prevCustomer = prevCustomer;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public String getRoomNumToString() {
		if (201 <= roomNum && roomNum <= 520)
			return String.valueOf(roomNum) + "호";
		if (65 <= roomNum && roomNum <= 90)
			return (char) roomNum + "실";
		if (91 == roomNum)
			return "VVIP실";
		return "";
	}

	public Type getRoomType() {
		return roomType;
	}

	public void setRoomType(Type roomType) {
		this.roomType = roomType;
	}

	public int getGuestNum() {
		return guestNum;
	}

	public void setGuestNum(int guestNum) {
		this.guestNum = guestNum;
	}

	public boolean isUseExtraBed() {
		return useExtraBed;
	}

	public void setUseExtraBed(boolean useExtraBed) {
		this.useExtraBed = useExtraBed;
	}

	public State getRoomState() {
		return roomState;
	}

	public void setRoomState(State roomState) {
		this.roomState = roomState;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer getReservationCustomer() {
		return reservationCustomer;
	}

	public void setReservationCustomer(Customer reservationCustomer) {
		this.reservationCustomer = reservationCustomer;
	}

	public Cleaning getCleaningState() {
		return cleaningState;
	}

	public void setCleaningState(Cleaning cleaningState) {
		this.cleaningState = cleaningState;
	}

	/**
	 * 객실의 상태를 출력하는 함수
	 */
	public void printState() {
		if (201 <= roomNum && roomNum <= 520) {
			System.out.println("<" + roomNum + "호>");
		} else if (65 <= roomNum && roomNum <= 90) {
			System.out.println("<" + (char) roomNum + "실>");
		} else if (roomNum == 91) {
			System.out.println("<VVIP실>");
		}
		switch (roomState) { // 방 상황
		case EMPTY:
			System.out.println("-빈 객실");
			break;
		case RESERVED:
			System.out.println("-예약된 객실");
			break;
		case OCCUPIED:
			System.out.println("-투숙중인 객실");
			break;
		case CLOSED:
			System.out.println("-폐쇄된 객실");
		}
		// 엑스트라 배드 가용 여부, 수용 인원, 침대 타입 문자열 반환
		System.out.println(getRoomAllInfo());
		System.out.println("청소 상태 : " + getCleaningStateString());

		if (reservationCustomer != null) {
			System.out.println("-예약자 정보");
			System.out.println("고객명 : " + reservationCustomer.getName());
			System.out.println("전화번호 : " + reservationCustomer.getPhoneNum());
			System.out.println("생년월일 : " + reservationCustomer.getBirth());
		}
		if (customer != null) { // 고객 출력
			System.out.println("-사용자 정보");
			System.out.println("고객명 : " + customer.getName());
			System.out.println("전화번호 : " + customer.getPhoneNum());
			System.out.println("생년월일 : " + customer.getBirth());
		}

		if (useExtraBed)
			System.out.println("[엑스트라 베드 사용]");
		if (guestNum > 0)
			System.out.println("사용 인원 : " + guestNum + "/" + getCapacity() + "명");
	}

	public String getRoomStateString() { // 객실 상태 문자열로 반환
		switch (roomState) {
		case EMPTY:
			return "[   ]";
		case RESERVED:
			return "[예약]";
		case OCCUPIED:
			return "[투숙]";
		case CLOSED:
			return "[폐쇄]";
		}
		return "";
	}

	public String getCleaningStateString() {
		switch (cleaningState) {
		case COMPLETE:
			return "[   ]";
		case NEED:
			return "[필요]";
		case INSPECTION:
			return "[점검]";
		case REMODELING:
			return "[리모]";
		}
		return "";
	}

	public int getRoomFee() {
		switch (roomType) {
		case STANDARD1:
			return 30000;
		case STANDARD2:
			return 40000;
		case SUPERIOR1:
			return 60000;
		case SUPERIOR2:
			return 70000;
		case DELUXE1:
			return 100000;
		case DELUXE2:
			return 110000;
		case EXECUTIVE1:
			return 140000;
		case EXECUTIVE2:
			return 150000;
		case SUITE:
			return 2_000_000;
		}
		return -1;
	}

	public String getRoomAllInfo() { // 룸 타입별 모든 정보 출력
		switch (roomType) { // 룸 타입
		case STANDARD1:
			return "<스탠다드1> <싱글1/1인/ExBed(N)>";
		case STANDARD2:
			return "<스탠다드2> <싱글2/3인/ExBed(Y)>";
		case SUPERIOR1:
			return "<슈페리어1> <더블1싱글1/4인/ExBed(Y)>";
		case SUPERIOR2:
			return "<슈페리어2> <더블2/5인/ExBed(Y)>";
		case DELUXE1:
			return "<디럭스1> <퀸1/2인/ExBed(N)>";
		case DELUXE2:
			return "<디럭스2> <킹1/2인/ExBed(N)>";
		case EXECUTIVE1:
			return "<이그젝큐티브1> <퀸1킹1/5인/ExBed(Y)>";
		case EXECUTIVE2:
			return "<이그젝큐티브2> <더블2킹1/7인/ExBed(Y)>";
		case SUITE:
			return "<스위트> <킹4/8인/ExBed(N)>";
		}
		return "";
	}

	public static String getRoomAllInfo(Type roomType) { // 룸 타입별 모든 정보 출력
		switch (roomType) { // 룸 타입
		case STANDARD1:
			return "<스탠다드1> <싱글1/1인/ExBed(N)>";
		case STANDARD2:
			return "<스탠다드2> <싱글2/3인/ExBed(Y)>";
		case SUPERIOR1:
			return "<슈페리어1> <더블1싱글1/4인/ExBed(Y)>";
		case SUPERIOR2:
			return "<슈페리어2> <더블2/5인/ExBed(Y)>";
		case DELUXE1:
			return "<디럭스1> <퀸1/2인/ExBed(N)>";
		case DELUXE2:
			return "<디럭스2> <킹1/2인/ExBed(N)>";
		case EXECUTIVE1:
			return "<이그젝큐티브1> <퀸1킹1/5인/ExBed(Y)>";
		case EXECUTIVE2:
			return "<이그젝큐티브2> <더블2킹1/7인/ExBed(Y)>";
		case SUITE:
			return "<스위트> <킹4/8인/ExBed(N)>";
		}
		return "";
	}

	public String getGradeOfRoom() { // 객실 등급 문자열로 반환
		switch (roomType) { // 룸 타입
		case STANDARD1:
			return "<스탠다드1>";
		case STANDARD2:
			return "<스탠다드2>";
		case SUPERIOR1:
			return "<슈페리어1>";
		case SUPERIOR2:
			return "<슈페리어2>";
		case DELUXE1:
			return "<디럭스1>";
		case DELUXE2:
			return "<디럭스2>";
		case EXECUTIVE1:
			return "<이그젝큐티브1>";
		case EXECUTIVE2:
			return "<이그젝큐티브2>";
		case SUITE:
			return "<스위트>";
		}
		return "";
	}

	public static String getGradeOfRoomInfo(Type roomType) { // 객실 등급 문자열로 반환
		switch (roomType) { // 룸 타입
		case STANDARD1:
			return "<스탠다드1>";
		case STANDARD2:
			return "<스탠다드2>";
		case SUPERIOR1:
			return "<슈페리어1>";
		case SUPERIOR2:
			return "<슈페리어2>";
		case DELUXE1:
			return "<디럭스1>";
		case DELUXE2:
			return "<디럭스2>";
		case EXECUTIVE1:
			return "<이그젝큐티브1>";
		case EXECUTIVE2:
			return "<이그젝큐티브2>";
		case SUITE:
			return "<스위트>";
		}
		return "";
	}

	public boolean canUseExtraBed() {
		switch (roomType) {
		case STANDARD1:
		case DELUXE1:
		case DELUXE2:
		case SUITE:
			return false;
		default:
			return true;
		}
	}

	public int getCapacity() {
		switch (roomType) {
		case STANDARD1:
			return 1;
		case DELUXE1:
		case DELUXE2:
			return 2;
		case STANDARD2:
			return 3;
		case SUPERIOR1:
			return 4;
		case SUPERIOR2:
		case EXECUTIVE1:
			return 5;
		case EXECUTIVE2:
			return 7;
		case SUITE:
			return 8;
		}
		return 0;
	}

	public void clear() {
		roomState = State.EMPTY;
		customer = null;
		reservationCustomer = null;
		guestNum = 0;
		useExtraBed = false;
	}
}