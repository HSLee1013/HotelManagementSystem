import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotelSystem {
	private final int FLOORS_OF_HOTEL_A = 4;
	private final int ROOMS_OF_HOTEL_A = 20;
	private final int ROOMS_OF_HOTEL_B = 27;
	private Scanner scanner;
	private Room[][] hotelA;
	private Room[] hotelB;
	private String adminPassword;
	private InputUtil inputUtil;
	private int money;
	private ArrayList<Cabinet> cabinet;

	public HotelSystem(String adminPassword) { // 생성자
		super();
		scanner = new Scanner(System.in);
		inputUtil = new InputUtil(scanner);
		hotelA = new Room[FLOORS_OF_HOTEL_A][ROOMS_OF_HOTEL_A];
		hotelB = new Room[ROOMS_OF_HOTEL_B];
		cabinet = new ArrayList<>();
		money = 0;
		this.adminPassword = adminPassword;

		for (int i = 0; i < 10; i++) {
			hotelA[0][i] = new Room(201 + i, Room.Type.STANDARD1);
		}
		for (int i = 10; i < 20; i++) {
			hotelA[0][i] = new Room(201 + i, Room.Type.STANDARD2);
		}
		for (int i = 0; i < 10; i++) {
			hotelA[1][i] = new Room(301 + i, Room.Type.DELUXE1);
		}
		for (int i = 10; i < 20; i++) {
			hotelA[1][i] = new Room(301 + i, Room.Type.DELUXE2);
		}
		for (int i = 0; i < 10; i++) {
			hotelA[2][i] = new Room(401 + i, Room.Type.SUPERIOR1);
		}
		for (int i = 10; i < 20; i++) {
			hotelA[2][i] = new Room(401 + i, Room.Type.SUPERIOR2);
		}
		for (int i = 0; i < 10; i++) {
			hotelA[3][i] = new Room(501 + i, Room.Type.EXECUTIVE1);
		}
		for (int i = 10; i < 19; i++) {
			hotelA[3][i] = new Room(501 + i, Room.Type.EXECUTIVE2);
		}
		hotelA[3][19] = new Room(520, Room.Type.SUITE);

		hotelA[2][3].setRoomState(Room.State.CLOSED);
		hotelA[2][13].setRoomState(Room.State.CLOSED);

		for (int i = 0; i < ROOMS_OF_HOTEL_B; i++) {
			hotelB[i] = new Room('A' + i);
		}
		for (int i = 0; i < 5; i++) {
			hotelB[i].setRoomType(Room.Type.STANDARD1);
		}
		for (int i = 5; i < 10; i++) {
			hotelB[i].setRoomType(Room.Type.DELUXE1);
		}
		for (int i = 10; i < 15; i++) {
			hotelB[i].setRoomType(Room.Type.STANDARD1);
		}
		for (int i = 15; i < 19; i++) {
			hotelB[i].setRoomType(Room.Type.DELUXE1);
		}
		for (int i = 19; i < 24; i++) {
			hotelB[i].setRoomType(Room.Type.STANDARD2);
		}
		for (int i = 24; i < 26; i++) {
			hotelB[i].setRoomType(Room.Type.SUPERIOR2);
		}

		hotelB[26].setRoomType(Room.Type.SUITE);
	}

	public void mainRun() { // 프로그램 실행
		while (true) {
			System.out.println("사용자 번호를 입력하시오.");
			System.out.println("[1] 관리자\n[2] 청소부\n[0] 프로그램 종료");
			int input = inputUtil.inputIntInRangeOrSelects(0, 2, 88224646, 9121000);
			switch (input) {
			case 0:
				System.out.println("프로그램을 종료합니다.");
				scanner.close();
				return;
			case 1:
				if (checkAdmin())
					managerRun();
				break;
			case 2:
				cleanerRun();
				break;
			case 88224646:
				System.out.println("현재 수익 : " + money + "원");
				break;
			case 9121000:
				if (!checkAdmin())
					break;
				System.out.println("변경할 비밀번호를 입력하시오.");
				String ps = scanner.nextLine();
				System.out.println("변경할 비밀번호가 [" + ps + "]가 맞습니까? [Y/N]");
				char inputYN = inputYN();
				if (inputYN == 'Y') {
					adminPassword = ps;
					System.out.println("비밀번호가 변경되었습니다.");
				} else {
					System.out.println("비밀번호 변경이 취소되었습니다.");
				}
				break;
			}
		}
	}

	private void managerRun() { // 관리자 실행
		while (true) {
			System.out.println("메뉴 번호를 입력하시오.");
			System.out.println("[1] 객실 상태 확인\n[2] 객실 상태 변경\n[3] 예약 관리\n[4] 물건 관리\n[0] 로그아웃");
			int input = inputUtil.inputIntInRange(0, 4);
			switch (input) {
			case 0:
				System.out.println("로그아웃 완료");
				return;
			case 1:
				checkRoomState();
				break;
			case 2:
				chageRoomState();
				break;
			case 3:
				manageReservation();
				break;
			case 4:
				manageCabinet();
				break;
			}
		}
	}

	private void manageCabinet() {
		System.out.println("메뉴 번호를 입력하시오.");
		System.out.println("[1] 물건 보관\n[2] 물건 찾기\n[0] 뒤로가기");
		int input = inputUtil.inputIntInRange(0, 2);
		switch (input) {
		case 0:
			return;
		case 1:
			keepItem();
			break;
		case 2:
			findItem();
			break;
		}
	}

	private void keepItem() {
		Customer customer = inputCustomer();
		System.out.println("보관할 물건을 입력하시오.");
		String item = scanner.nextLine();
		cabinet.add(new Cabinet(customer, item));
		System.out.println("물건 보관 완료");
	}

	private void findItem() {
		Customer customer = inputCustomer();
		if (!hasItemInCabinet(customer))
			System.out.println("보관하신 물품이 없습니다.");
		System.out.print("찾으신 물건 : ");
		for (int i = 0; i < cabinet.size(); i++) {
			if (cabinet.get(i).getCustomer().equals(customer)) {
				System.out.print("[" + cabinet.get(i).getItem() + "] ");
				cabinet.remove(i);
				i--;
			}
		}
		System.out.println();
	}

	private boolean hasItemInCabinet(Customer customer) {
		for (Cabinet c : cabinet) {
			if (c.getCustomer().equals(customer))
				return true;
		}
		return false;
	}

	private void cleanerRun() {
		while (true) {
			System.out.println("메뉴 번호를 입력하시오.");
			System.out.println("[1] 객실 상태 확인\n[2] 청소 필요 객실\n[3] 청소 상태 변경\n[0] 로그아웃");
			int input = inputUtil.inputIntInRange(0, 3);
			switch (input) {
			case 0:
				System.out.println("로그아웃 완료");
				return;
			case 1:
				checkBuildingForCleaner();
				break;
			case 2:
				needCleaningList();
				break;
			case 3:
				changeCleaningState();
				break;
			}
		}
	}

	private void needCleaningList() {
		Room room = selectCleaningRoom();
		if (room == null)
			return;
		room.setCleaningState(Room.Cleaning.COMPLETE);
		System.out.println("청소 완료");
		checkLostItem(room);
	}

	private void checkLostItem(Room room) {
		System.out.println("분실물이 있었습니까? [Y/N]");
		char input = inputYN();
		switch (input) {
		case 'Y':
			System.out.println("분실물을 입력하시오.");
			String item = scanner.nextLine();
			cabinet.add(new Cabinet(room.getPrevCustomer(), item));
			System.out.println("분실물 보관 완료");
			break;
		case 'N':
			break;
		}
	}

	private Room[] getCleaningRooms() {
		// 리스트 생성
		List<Room> roomList = new ArrayList<>();
		
		// 반복 수행
		for (int i = 0; i < hotelA.length; i++) {
			for (int j = 0; j < hotelA[i].length; j++) {
				// 방 참조 room
				Room room = hotelA[i][j];
				// room 상태 확인
				if (room.getCleaningState() == Room.Cleaning.NEED) {
					if (room.getRoomState() != Room.State.OCCUPIED)
						// 리스트에 방 참조 추가
						roomList.add(room);
				}
			}
		}
		
		// 위 동일과정 B 호텔 반복 수행
		for (int i = 0; i < hotelB.length; i++) {
			Room room = hotelB[i];
			if (room.getCleaningState() == Room.Cleaning.NEED) {
				if (room.getRoomState() != Room.State.OCCUPIED)
					roomList.add(room);
			}
		}
		// 배열로 반환
		return roomList.toArray(new Room[0]);
	}

	private Room selectCleaningRoom() { // 청소 목록 출력
		// Room.setCleanningState();
		Room[] cleaningRooms = getCleaningRooms();

		System.out.println("-청소 필요 객실");
		System.out.println("청소할 객실을 선택하시오.");
		for (int i = 0; i < cleaningRooms.length; i++) {
			System.out.println("[" + (i + 1) + "] " + cleaningRooms[i].getRoomNumToString());
		}
		System.out.println("[0] 뒤로가기");
		int input = inputUtil.inputIntInRange(0, cleaningRooms.length);
		if (input == 0)
			return null;
		return cleaningRooms[input - 1];
	}

	private void changeCleaningState() { // 청소 상태 변경
		Room cleaningRoom = selectRoom();
		if (cleaningRoom == null)
			return;
		System.out.println("청소 상태를 무엇으로 변경하시겠습니까?");
		Room.Cleaning[] rc = new Room.Cleaning[4];
		int num = 0;
		if (cleaningRoom.getCleaningState() != Room.Cleaning.COMPLETE) {
			System.out.println("[" + (num + 1) + "] 청소완료");
			rc[num] = Room.Cleaning.COMPLETE;
			num++;
		}
		if (cleaningRoom.getCleaningState() != Room.Cleaning.NEED) {
			System.out.println("[" + (num + 1) + "] 청소필요");
			rc[num] = Room.Cleaning.NEED;
			num++;
		}
		if (cleaningRoom.getCleaningState() != Room.Cleaning.INSPECTION) {
			System.out.println("[" + (num + 1) + "] 정비필요");
			rc[num] = Room.Cleaning.INSPECTION;
			num++;
		}
		if (cleaningRoom.getCleaningState() != Room.Cleaning.REMODELING) {
			System.out.println("[" + (num + 1) + "] 리모델링");
			rc[num] = Room.Cleaning.REMODELING;
			num++;
		}
		System.out.println("[0] 뒤로가기");
		int input = inputUtil.inputIntInRange(0, 4);
		if (input == 0) {
			return;
		}
		cleaningRoom.setCleaningState(rc[input - 1]);
		if (rc[input - 1] == Room.Cleaning.COMPLETE)
			checkLostItem(cleaningRoom);
	}

	private boolean checkAdmin() { // 비밀번호 확인
		System.out.println("비밀번호를 입력하시오.");
		if (adminPassword.equals(scanner.nextLine())) {
			return true;
		} else {
			System.out.println("비밀번호가 틀렸습니다.");
			return false;
		}
	}

	private void checkBuildingForCleaner() {
		int select = selectBuilding();
		switch (select) {
		case 1:
			checkFloorState();
			break;
		case 2:
			printFloorStateB();
			break;
		}
	}

	private void checkFloorState() { // 청소부가 층의 상태 확인
		int floor = selectFloor();
		if (floor == 0) {
			System.out.println("취소하셨습니다.");
			return;
		}
		printFloorStateA(floor);
	}

	private void printFloorStateA(int floor) { // 층의 상태 출력
		Room.Type prevRoomType = null;
		int prev = 0;
		int print = 0;
		System.out.println("A동 " + floor + "층");
		for (int i = 0; i < hotelA[floor - 2].length; i++) {
			Room room = hotelA[floor - 2][i];
			Room.Type roomType = room.getRoomType();
			if (roomType != prevRoomType) {
				if (prevRoomType == null) {
					System.out.println(room.getRoomAllInfo());
					System.out.print("(방)\t");
				} else {
					System.out.println();
					if (print == 2) {
						print = 0;
						prev = i;
						System.out.println(room.getRoomAllInfo());
						System.out.print("(방)\t");
					} else {
						print++;
						i = prev - 1;
						if (print == 1)
							System.out.print("(상태)\t");
						if (print == 2)
							System.out.print("(청소)\t");
						continue;
					}
				}
			}
			if (print == 0)
				System.out.print(room.getRoomNumToString() + "\t");
			else if (print == 1)
				System.out.print(room.getRoomStateString() + "\t");
			else
				System.out.print(room.getCleaningStateString() + "\t");
			prevRoomType = roomType;
			if (i == hotelA[floor - 2].length - 1 && print < 2) {
				System.out.println();
				i = prev - 1;
				print++;
				if (print == 1)
					System.out.print("(상태)\t");
				if (print == 2)
					System.out.print("(청소)\t");
			}
		}
		System.out.println();

	}

	private void printFloorStateB() { // 층의 상태 출력
		Room.Type prevRoomType = null;
		int prev = 0;
		int print = 0;
		for (int i = 0; i < hotelB.length; i++) {
			Room room = hotelB[i];
			Room.Type roomType = room.getRoomType();
			if (roomType != prevRoomType) {
				if (prevRoomType == null) {
					System.out.println("B동 1층");
					System.out.println(room.getRoomAllInfo());
					System.out.print("(방)\t");
				} else {
					System.out.println();
					if (print == 2) {
						print = 0;
						prev = i;
						if (i == 10) {
							System.out.println("\nB동 2층");
						}
						if (i == 19) {
							System.out.println("\nB동 3층");
						}
						System.out.println(room.getRoomAllInfo());
						System.out.print("(방)\t");
					} else {
						print++;
						i = prev - 1;
						if (print == 1)
							System.out.print("(상태)\t");
						if (print == 2)
							System.out.print("(청소)\t");
						continue;
					}
				}
			}
			if (print == 0)
				System.out.print(room.getRoomNumToString() + "\t");
			else if (print == 1)
				System.out.print(room.getRoomStateString() + "\t");
			else
				System.out.print(room.getCleaningStateString() + "\t");
			prevRoomType = roomType;
			if (i == hotelB.length - 1 && print < 2) {
				System.out.println();
				i = prev - 1;
				print++;
				if (print == 1)
					System.out.print("(상태)\t");
				if (print == 2)
					System.out.print("(청소)\t");
			}
		}
		System.out.println();
	}

	private int selectFloor() { // 층 선택해서 인트로 반환 (배열+2 반환)
		System.out.println("층을 선택하시오. [2~" + (1 + FLOORS_OF_HOTEL_A) + "] 취소 [0]");
		return inputUtil.inputIntInRangeOrSelects(2, 1 + FLOORS_OF_HOTEL_A, 0);
	}

	private Room selectRoomForWalkIn() {
		int input = 0;
		int floor = 0;
		do {
			floor = selectFloor();
			if (floor == 0)
				return null;
			printFloorStateA(floor);
			System.out.println("객실을 선택하시오. [" + floor + "01~" + floor + "20] 층 선택 [1] 취소 [0]");
			input = inputUtil.inputIntInRangeOrSelects(floor * 100 + 1, floor * 100 + 20, 0, 1);
			if (input == 0) {
				return null;
			}
		} while (input == 1);

		return hotelA[floor - 2][input - floor * 100 - 1];
	}

	private Room selectRoom() { // 객실을 선택해서 Room 반환
		int input = 0;
		int floor = 0;
		int select = 0;
		select = selectBuilding();
		if (select == 1) {
			do {
				floor = selectFloor();
				if (floor == 0)
					return null;
				printFloorStateA(floor);
				System.out.println("객실을 선택하시오. [" + floor + "01~" + floor + "20] 층 선택 [1] 취소 [0]");
				input = inputUtil.inputIntInRangeOrSelects(floor * 100 + 1, floor * 100 + 20, 0, 1);
				if (input == 0) {
					return null;
				}
			} while (input == 1);

			return hotelA[floor - 2][input - floor * 100 - 1];

		} else if (select == 2) {
			printFloorStateB();
			System.out.println("객실을 선택하시오. [A ~ Z, VVIP] 취소 [0]");
			while (true) {
				String inputString = scanner.nextLine().toUpperCase();
				if (inputString.equals("VVIP")) {
					return hotelB[26];
				}
				if (inputString.equals("0")) {
					return null;
				} else {
					if (inputString.length() == 1) {
						char c = inputString.charAt(0);
						if ('A' <= c && c <= 'Z')
							return hotelB[inputString.charAt(0) - 65];
					}
				}
				System.out.println("잘못된 입력");
			}
		}
		return null;
	}

	private int selectBuilding() {
		System.out.println("A동 B동을 선택하시오.");
		System.out.println("[1] A동\n[2] B동\n[0] 취소");
		return inputUtil.inputIntInRangeOrSelects(0, 2);
	}

	private void checkRoomState() { // 관리자가 객실 상태 확인
		Room room = selectRoom();
		if (room == null) { // 취소한 상황
			System.out.println("취소하셨습니다.");
			return;
		}
		room.printState();
	}

	private void chageRoomState() { // 객실 상태 변경
		System.out.println("메뉴 번호를 입력하시오.");
		System.out.println("[1] 워크 인\n[2] 체크 인\n[3] 체크 아웃\n[4] 룸 체인지 \n[5] 룸 업그레이드 \n[0] 뒤로가기");
		int changeRoomStateNum = inputUtil.inputIntInRange(0, 5);
		switch (changeRoomStateNum) {
		case 0:
			break;
		case 1:
			walkIn();
			break;
		case 2:
			checkIn();
			break;
		case 3:
			checkOut();
			break;
		case 4:
			roomChange();
			break;
		case 5:
			roomUpgrade();
			break;
		}
	}

	private void walkIn() { // 워크 인
		Room room;
		while (true) {
			room = selectRoomForWalkIn();
			if (room == null) { // 취소한 상황
				System.out.println("변경을 취소하셨습니다.");
				return;
			}
			if (room.getRoomState() == Room.State.EMPTY && room.getCleaningState() == Room.Cleaning.COMPLETE)
				break;
			System.out.println("투숙할 수 없는 객실입니다.");
		}

		Customer customer = inputCustomer();

		boolean useExtraBed = false;
		if (room.canUseExtraBed())
			useExtraBed = inputUseExtraBed();

		System.out.println("가격은 " + room.getRoomFee() + "원 입니다.");
		System.out.println("워크 인 하시겠습니까? [Y/N]");
		char input = inputYN();
		switch (input) {
		case 'Y':
			room.setCustomer(customer);
			room.setRoomState(Room.State.OCCUPIED);
			room.setUseExtraBed(useExtraBed);
			money += room.getRoomFee();
			System.out.println("워크 인 되었습니다.");
			break;
		case 'N':
			System.out.println("변경을 취소하셨습니다.");
			break;
		}
	}

	private void checkIn() { // 체크 인 [타입] [싱글1더블2/7/N]
		Customer customer = inputCustomer();

		if (!hasReservedRoomFromUser(customer)) {
			System.out.println("예약된 정보가 없습니다.");
			return;
		}

		Room room = selectReservedRoomFromUser(customer);

		if (room.getCleaningState() != Room.Cleaning.COMPLETE) {
			System.out.println("아직 방이 준비가 안됐습니다.");
			return;
		}

		boolean useExtraBed = false;
		if (room.canUseExtraBed())
			if (!room.isUseExtraBed())
				useExtraBed = inputUseExtraBed();

		System.out.println("가격은 " + room.getRoomFee() + "원 입니다.");
		System.out.println("체크인 하시겠습니까? [Y/N]");
		char checkInYesOrNo1 = inputYN();

		switch (checkInYesOrNo1) {
		case 'Y':
			room.setReservationCustomer(null);
			room.setRoomState(Room.State.OCCUPIED);
			room.setUseExtraBed(useExtraBed);
			money += room.getRoomFee();
			break;
		case 'N':
			System.out.println("변경을 취소하셨습니다.");
			break;
		}
	}

	private void checkOut() { // 체크 아웃
		Customer customer = inputCustomer();

		if (!hasOccupiedRoom(customer)) {
			System.out.println("투숙중인 방이 없습니다.");
			return;
		}

		Room room = selectOccupiedRoom(customer);

		System.out.println("체크아웃 하시겠습니까? [Y/N]");
		char input = inputYN();

		switch (input) {
		case 'Y':
			room.setPrevCustomer(customer);
			room.clear();
			room.setCleaningState(Room.Cleaning.NEED);
			break;
		case 'N':
			System.out.println("변경을 취소하셨습니다.");
			break;
		}
	}

	private void roomChange() {
		Customer customer = inputCustomer();
		if (!hasOccupiedRoom(customer)) {
			System.out.println("변경 가능한 객실이 없습니다.");
			return;
		}
		Room prevRoom = selectOccupiedRoom(customer);
		Room.Type prevRoomType = prevRoom.getRoomType();
		if (!hasChangedRoom(prevRoomType)) {
			System.out.println("변경 가능한 객실이 없습니다.");
			return;
		}

		Room nextRoom = selectChangedRoom(prevRoomType);

		System.out.println("변경하시겠습니까? [Y/N]");
		char input = inputYN();
		switch (input) {
		case 'Y':
			nextRoom.setUseExtraBed(prevRoom.isUseExtraBed());
			nextRoom.setCustomer(customer);
			nextRoom.setRoomState(Room.State.OCCUPIED);
			prevRoom.clear();
			break;
		case 'N':
			break;
		}
	}

	private Room[] getChangedRooms(Room.Type roomType) {
		List<Room> roomList = new ArrayList<>();
		for (int i = 0; i < hotelA.length; i++) {
			for (int j = 0; j < hotelA[i].length; j++) {
				Room room = hotelA[i][j];
				if (room.getRoomState() == Room.State.EMPTY && room.getCleaningState() == Room.Cleaning.COMPLETE) {
					if (room.getRoomType() == roomType)
						roomList.add(room);
				}
			}
		}
		return roomList.toArray(new Room[0]);
	}

	private Room selectChangedRoom(Room.Type roomType) {
		Room[] changedRooms = getChangedRooms(roomType);
		System.out.println("변경할 객실을 선택하시오.");
		for (int i = 0; i < changedRooms.length; i++) {
			System.out.print("[" + (i + 1) + "] " + changedRooms[i].getRoomNumToString());
			if ((i + 1) % 5 == 0)
				System.out.println();
			if ((i + 1) % 5 != 0 && i == changedRooms.length - 1)
				System.out.println();
		}
		int input = inputUtil.inputIntInRange(1, changedRooms.length);
		return changedRooms[input - 1];
	}

	// 객실상태 변경 추가 룸체인지
	private boolean hasChangedRoom(Room.Type roomType) {
		for (int i = 0; i < hotelA.length; i++) {
			for (int j = 0; j < hotelA[i].length; j++) {
				Room room = hotelA[i][j];
				if (room.getRoomType() == roomType) {
					if (room.getRoomState() == Room.State.EMPTY)
						return true;
				}
			}
		}
		return false;
	}

	private void roomUpgrade() {
		Customer customer = inputCustomer();
		if (!hasOccupiedRoom(customer)) {
			System.out.println("업그레이드 가능한 방이 없습니다.");
			return;
		}

		Room prevRoom = selectOccupiedRoom(customer);
		Room.Type prevRoomType = prevRoom.getRoomType();
		if (!hasUpgradeRoom(prevRoomType)) {
			System.out.println("업그레이드 가능한 방이 없습니다.");
			return;
		}
		Room nextRoom = selectUpgradedRoom(prevRoomType);

		if (nextRoom.getCapacity() < prevRoom.getCapacity()) {
			System.out.println("투숙 가능 인원이 " + nextRoom.getCapacity() + "로 줄어듭니다.");
			System.out.println("상관없으십니까? [Y/N]");
			char input = inputYN();
			if (input == 'N')
				return;
		}

		System.out.println("업그레이드 하시겠습니까? [Y/N]");
		char input = inputYN();
		switch (input) {
		case 'Y':
			nextRoom.setCustomer(customer);
			nextRoom.setRoomState(Room.State.OCCUPIED);
			prevRoom.clear();
			System.out.println("업그레이드 되었습니다.");
			break;
		case 'N':
			System.out.println("변경을 취소하셨습니다.");
			break;
		}
	}

	private boolean hasUpgradeRoom(Room.Type roomType) {
		for (int i = 0; i < hotelA.length; i++) {
			for (int j = 0; j < hotelA[i].length; j++) {
				Room room = hotelA[i][j];
				if (room.getRoomType().compareTo(roomType) > 0) {
					if (room.getRoomState() == Room.State.EMPTY)
						return true;
				}
			}
		}
		return false;
	}

	private Room selectUpgradedRoom(Room.Type roomType) {
		Room.Type upgradedRoomType = selectUpgradeType(roomType);
		Room[] upgradedRooms = getChangedRooms(upgradedRoomType);
		System.out.println("업그레이드할 객실을 선택하시오.");
		for (int i = 0; i < upgradedRooms.length; i++) {
			System.out.print("[" + (i + 1) + "] " + upgradedRooms[i].getRoomNumToString());
			if ((i + 1) % 5 == 0)
				System.out.println();
			if ((i + 1) % 5 != 0 && i == upgradedRooms.length - 1)
				System.out.println();
		}
		int input = inputUtil.inputIntInRange(1, upgradedRooms.length);
		return upgradedRooms[input - 1];
	}

	private Room.Type[] getUpgradeTypes(Room.Type roomType) {
		List<Room.Type> typeList = new ArrayList<>();
		for (Room.Type rt : Room.Type.values()) {
			if (rt.compareTo(roomType) > 0)
				if (hasChangedRoom(rt))
					typeList.add(rt);
		}
		return typeList.toArray(new Room.Type[0]);
	}

	private Room.Type selectUpgradeType(Room.Type roomType) {
		Room.Type[] roomTypes = getUpgradeTypes(roomType);
		for (int i = 0; i < roomTypes.length; i++) {
			System.out.println("[" + (i + 1) + "] " + Room.getRoomAllInfo(roomTypes[i]));
		}
		int input = inputUtil.inputIntInRange(1, roomTypes.length);
		return roomTypes[input - 1];
	}

	private boolean hasOccupiedRoom(Customer customer) {
		for (int i = 0; i < hotelA.length; i++) {
			for (int j = 0; j < hotelA[i].length; j++) {
				Room room = hotelA[i][j];
				if (room.getRoomState() == Room.State.OCCUPIED) {
					if (room.getCustomer().equals(customer))
						return true;
				}
			}
		}
		return false;
	}

	private Room selectOccupiedRoom(Customer customer) {
		Room[] occupiedRooms = getOccupiedRoom(customer);
		System.out.println("투숙 중인 객실을 선택하시오.");
		for (int i = 0; i < occupiedRooms.length; i++) {
			System.out.println("[" + (i + 1) + "] " + occupiedRooms[i].getRoomNumToString());
		}
		int input = inputUtil.inputIntInRange(1, occupiedRooms.length);
		return occupiedRooms[input - 1];
	}

	private Room[] getOccupiedRoom(Customer customer) {
		List<Room> roomList = new ArrayList<>();
		for (int i = 0; i < hotelA.length; i++) {
			for (int j = 0; j < hotelA[i].length; j++) {
				Room room = hotelA[i][j];
				if (room.getRoomState() == Room.State.OCCUPIED) {
					if (room.getCustomer().equals(customer))
						roomList.add(room);
				}
			}
		}
		return roomList.toArray(new Room[0]);
	}

	private void manageReservation() { // 예약 관리
		System.out.println("메뉴 번호를 입력하시오.");
		System.out.println("[1] 예약하기\n[2] 예약변경\n[3] 예약취소\n[0] 뒤로가기");
		int s = inputUtil.inputIntInRange(0, 3);
		switch (s) {
		case 1:
			addReservation();
			break;
		case 2:
			changeReservation();
			break;
		case 3:
			cancelReservation();
			break;
		case 0:
			break;
		}
	}

	private void addReservation() {
		Room room;
		while (true) {
			room = selectRoom();
			if (room == null) { // 취소한 상황
				System.out.println("변경을 취소하셨습니다.");
				return;
			}
			if (room.getRoomState() == Room.State.EMPTY) {
				System.out.println("예약이 가능한 방입니다.");
				break;
			} else {
				System.out.println("예약이 불가능합니다.");
			}
		}

		if (room.getRoomNum() == 91) {
			System.out.println("VVIP 코드를 입력하시오.");
			String code = scanner.nextLine();
			if (!code.equals("VV1P")) {
				System.out.println("VVIP코드가 틀렸습니다.");
				return;
			}
		}

		System.out.println("예약 인원을 입력해주시오.");
		int guestNum = inputUtil.inputInt();
		if (room.getCapacity() < guestNum) {
			System.out.println("수용 가능 인원을 초과하셨습니다.");
			return;
		}

		System.out.println("-예약자 정보 입력");
		Customer reservationCustomer = inputCustomer();
		System.out.println("사용자 본인입니까? [Y/N]");
		Customer customer = null;
		char input = inputYN();
		switch (input) {
		case 'Y':
			customer = reservationCustomer;
			break;
		case 'N':
			System.out.println("-사용자 정보 입력");
			customer = inputCustomer();
			break;
		}

		boolean useExtraBed = false;
		if (room.canUseExtraBed())
			useExtraBed = inputUseExtraBed();

		System.out.println("예약을 하시겠습니까? [Y/N]");
		input = inputYN();
		switch (input) {
		case 'Y':
			room.setReservationCustomer(reservationCustomer);
			room.setCustomer(customer);
			room.setRoomState(Room.State.RESERVED);
			room.setUseExtraBed(useExtraBed);
			System.out.println("예약되었습니다.");
			break;
		case 'N':
			System.out.println("변경을 취소하셨습니다.");
			break;
		}
	}

	private boolean inputUseExtraBed() {
		System.out.println("엑스트라 베드를 신청 하시겠습니까? [Y/N]");
		char input = inputYN();
		switch (input) {
		case 'Y':
			return true;
		case 'N':
			return false;
		}
		return false;
	}

	private void changeReservation() {
		Customer reservationCustomer = inputCustomer();
		if (!hasReservedRoomFromReservation(reservationCustomer)) {
			System.out.println("예약된 정보가 없습니다.");
			return;
		}

		System.out.println("-변경할 객실 선택");
		Room reservedRoom = selectReservedRoomFromReservation(reservationCustomer);

		System.out.println("[1] 객실 변경 [2] 사용자 변경 [0] 취소");
		int input = inputUtil.inputIntInRange(0, 2);
		switch (input) {
		case 0:
			return;
		case 1:
			changeReservationRoom(reservedRoom, reservationCustomer);
			break;
		case 2:
			changeReservationCustomer(reservedRoom);
			break;
		}
	}

	private void changeReservationRoom(Room reservedRoom, Customer reservationCustomer) {
		Room room;
		while (true) {
			room = selectRoom();
			if (room == null) { // 취소한 상황
				System.out.println("변경을 취소하셨습니다.");
				return;
			}
			if (room.getRoomState() == Room.State.EMPTY) {
				System.out.println("예약이 가능한 방입니다.");
				break;
			} else {
				System.out.println("예약이 불가능합니다.");
			}
		}

		System.out.println("예약을 변경하시겠습니까? [Y/N]");
		char inputYN = inputYN();
		switch (inputYN) {
		case 'Y':
			room.setReservationCustomer(reservationCustomer);
			room.setCustomer(reservedRoom.getCustomer());
			room.setRoomState(Room.State.RESERVED);
			reservedRoom.clear();
			System.out.println("예약이 변경되었습니다.");
			break;
		case 'N':
			System.out.println("변경을 취소하셨습니다.");
			break;
		}
	}

	private void changeReservationCustomer(Room reservedRoom) {
		System.out.println("-사용자 정보 입력");
		Customer customer = inputCustomer();
		System.out.println("사용자를 변경하시겠습니까? [Y/N]");
		char inputYN = inputYN();
		switch (inputYN) {
		case 'Y':
			reservedRoom.setCustomer(customer);
			break;
		case 'N':
			System.out.println("변경을 취소하셨습니다.");
			break;
		}
	}

	private void cancelReservation() { // 예약 취소
		Customer reservationCustomer = inputCustomer();
		if (!hasReservedRoomFromReservation(reservationCustomer)) {
			System.out.println("예약된 정보가 없습니다.");
			return;
		}

		Room reservedRoom = selectReservedRoomFromReservation(reservationCustomer);

		System.out.println("예약을 취소하시겠습니까? [Y/N]");
		char input = inputYN();
		switch (input) {
		case 'Y':
			reservedRoom.clear();
			System.out.println("예약이 취소되었습니다.");
			break;
		case 'N':
			System.out.println("변경을 취소하셨습니다.");
			break;
		}
	}

	private Room[] getReservedRoomFromReservation(Customer reservationCustomer) { // 예약자로 예약한 객실 목록 배열로 반환
		List<Room> roomList = new ArrayList<>();
		for (int i = 0; i < hotelA.length; i++) {
			for (int j = 0; j < hotelA[i].length; j++) {
				Room room = hotelA[i][j];
				if (room.getRoomState() == Room.State.RESERVED) {
					if (room.getReservationCustomer().equals(reservationCustomer))
						roomList.add(room);
				}
			}
		}
		for (int i = 0; i < hotelB.length; i++) {
			Room room = hotelB[i];
			if (room.getRoomState() == Room.State.RESERVED) {
				if (room.getReservationCustomer().equals(reservationCustomer))
					roomList.add(room);
			}
		}
		return roomList.toArray(new Room[0]);
	}

	private Room selectReservedRoomFromReservation(Customer reservationCustomer) { // 예약자로 예약한 객실 중 선택해서 반환
		Room[] reservedRooms = getReservedRoomFromReservation(reservationCustomer);
		System.out.println("예약된 객실을 선택하시오.");
		for (int i = 0; i < reservedRooms.length; i++) {
			System.out.println("[" + (i + 1) + "] " + reservedRooms[i].getRoomNumToString());
		}
		int input = inputUtil.inputIntInRange(1, reservedRooms.length);
		return reservedRooms[input - 1];
	}

	private Room[] getReservedRoomFromUser(Customer customer) { // 사용자로 예약한 객실
																// 목록 배열로 반환
		List<Room> roomList = new ArrayList<>();
		for (int i = 0; i < hotelA.length; i++) {
			for (int j = 0; j < hotelA[i].length; j++) {
				Room room = hotelA[i][j];
				if (room.getRoomState() == Room.State.RESERVED) {
					if (room.getCustomer().equals(customer))
						roomList.add(room);
				}
			}
		}
		for (int i = 0; i < hotelB.length; i++) {
			Room room = hotelB[i];
			if (room.getRoomState() == Room.State.RESERVED) {
				if (room.getCustomer().equals(customer))
					roomList.add(room);
			}
		}
		return roomList.toArray(new Room[0]);
	}

	private Room selectReservedRoomFromUser(Customer customer) { // 사용자로 예약한 객실 중 선택해서 반환
		Room[] reservedRooms = getReservedRoomFromUser(customer);
		System.out.println("예약된 객실을 선택하시오.");
		for (int i = 0; i < reservedRooms.length; i++) {
			System.out.print("[" + (i + 1) + "] " + reservedRooms[i].getRoomNumToString());
			if (reservedRooms[i].getCleaningState() != Room.Cleaning.COMPLETE)
				System.out.println(" [준비안됨]");
			else
				System.out.println();
		}
		int input = inputUtil.inputIntInRange(1, reservedRooms.length);
		return reservedRooms[input - 1];
	}

	private boolean hasReservedRoomFromReservation(Customer reservationCustomer) {
		for (int i = 0; i < hotelA.length; i++) {
			for (int j = 0; j < hotelA[i].length; j++) {
				Room room = hotelA[i][j];
				if (room.getRoomState() == Room.State.RESERVED) {
					if (room.getReservationCustomer().equals(reservationCustomer))
						return true;
				}
			}
		}
		for (int i = 0; i < hotelB.length; i++) {
			Room room = hotelB[i];
			if (room.getRoomState() == Room.State.RESERVED) {
				if (room.getReservationCustomer().equals(reservationCustomer))
					return true;
			}
		}
		return false;
	}

	private boolean hasReservedRoomFromUser(Customer customer) {
		for (int i = 0; i < hotelA.length; i++) {
			for (int j = 0; j < hotelA[i].length; j++) {
				Room room = hotelA[i][j];
				if (room.getRoomState() == Room.State.RESERVED) {
					if (room.getCustomer().equals(customer))
						return true;
				}
			}
		}
		for (int i = 0; i < hotelB.length; i++) {
			Room room = hotelB[i];
			if (room.getRoomState() == Room.State.RESERVED) {
				if (room.getCustomer().equals(customer))
					return true;
			}
		}
		return false;
	}

	// 고객 입력 받아서 고객 생성하여 반환
	private Customer inputCustomer() {
		String name;
		String birth;
		String phoneNum;
		System.out.println("고객 정보를 입력합니다.");
		System.out.println("이름을 입력하시오.");
		name = inputUtil.inputName();
		System.out.println("생년월일을 입력하시오(년월일 여덟자리로 입력)");
		birth = inputUtil.inputDate();
		System.out.println("전화번호를 입력하시오.(-포함)");
		phoneNum = inputUtil.inputPhonNumber();
		System.out.println("입력된 정보");
		System.out.println("이름 : " + name);
		System.out.println("생년월일 : " + birth);
		System.out.println("전화번호 : " + phoneNum);
		System.out.println("입력하신 내용이 맞습니까? [Y/N]");
		char input = inputYN();
		if (input == 'N')
			return inputCustomer();
		return new Customer(name, birth, phoneNum);
	}

	private char inputYN() { // Y/N을 받는 함수
		while (true) {
			String strInput = scanner.nextLine();
			switch (strInput) {
			case "y":
			case "Y":
				return 'Y';
			case "n":
			case "N":
				return 'N';
			default:
				System.out.println("잘못된 입력 [다른 문자|공백 X]");
			}
		}
	}

}
