public class LinkedList {

    private static class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head = null; // 첫 번째 노드를 가리키는 포인터

    // 기본은 가장 마지막에 삽입
    public void add(int value) {
        Node newNode = new Node(value);

        // 생성된 노드 없음
        if (head == null) {
            head = newNode;
        }
        else {
            // tail이라는 포인터를 추가하면 O(1)만에 삽입 가능
            Node currNode = head;
            while (currNode.next != null) { // 가장 마지막 노드까지 이동
                currNode = currNode.next;
            }
            currNode.next = newNode;
        }
    }

    // 특정 인덱스 위치에 삽입
    public void add(int index, int value) {
        // 생성된 노드 없음
        if (head == null) { // tail 사용 시 마지막도 append를 바로 이용하면 됨
            add(value); // 기존 메서드 이용
            return;
        }

        // 맨 앞에 삽입
        if (index == 0) {
            Node newNode = new Node(value);
            newNode.next = head;
            head = newNode;
        }
        // 중간이나 맨 뒤에 삽입
        else {
            // 삽입할 위치의 바로 전 노드 찾기
            Node prevNode = head;
            for (int i = 0; i < index - 1; i++) {
                if (prevNode.next == null) { // 존재하지 않는 인덱스
                    System.out.println("인덱스 범위를 벗어났습니다.");
                    return;
                }
                prevNode = prevNode.next;
            }

            Node newNode = new Node(value);
            newNode.next = prevNode.next;
            prevNode.next = newNode;
        }
    }

    // 기본은 가장 앞 노드 삭제
    public int remove() {
        if (head == null) {
            System.out.println("빈 리스트입니다.");
            return -1;
        }

        int firstData = head.data;

        head = head.next; // 삭제된 노드는 GC가 알아서 삭제해 줌

        return firstData;
    }

    // 특정 값 삭제
    public int remove(int value) {
        if (head == null) {
            System.out.println("빈 리스트입니다.");
            return -1;
        }

        // 삭제할 노드가 가장 맨 앞
        if (head.data == value) {
            head = head.next;
            return value;
        }
        
        // 삭제할 노드의 바로 전 노드 찾기
        Node prevNode = head;
        while (prevNode.next != null) {
            if (prevNode.next.data == value) {
                prevNode.next = prevNode.next.next; // 중간 노드 연결 끊기
                return value;
            }
            
            prevNode = prevNode.next;
        }
        System.out.println("값을 찾을 수 없습니다.");
        return -1;
    }

    // 링크드리스트 출력
    public void printList() {
        Node currNode = head;
        while (currNode != null) {
            System.out.print(currNode.data + " -> ");
            currNode = currNode.next;
        }
        System.out.println("null");
    }

    // 실행을 위한 main 메서드
    public static void main(String[] args) {
        LinkedList list = new LinkedList();

        System.out.println("=== 1. 순서대로 삽입 (append) ===");
        list.add(10);
        list.add(20);
        list.add(30);
        list.printList(); // 10 -> 20 -> 30 -> null

        System.out.println("\n=== 2. 특정 인덱스에 삽입 ===");
        System.out.println("[인덱스 0에 5 삽입]");
        list.add(0, 5);
        list.printList(); // 5 -> 10 -> 20 -> 30 -> null

        System.out.println("[인덱스 2에 15 삽입]");
        list.add(2, 15);
        list.printList(); // 5 -> 10 -> 15 -> 20 -> 30 -> null

        System.out.println("[인덱스 100에 삽입 시도 (예외 처리 확인)]");
        list.add(100, 999); // 에러 메시지 출력되어야 함

        System.out.println("\n=== 3. 기본 삭제 (맨 앞 삭제) ===");
        System.out.println("삭제된 값: " + list.remove()); // 5 삭제됨
        list.printList(); // 10 -> 15 -> 20 -> 30 -> null

        System.out.println("\n=== 4. 특정 값 삭제 ===");
        System.out.println("[맨 앞 노드인 10 삭제 시도]");
        list.remove(10);
        list.printList(); // 15 -> 20 -> 30 -> null

        System.out.println("[중간 노드인 20 삭제 시도]");
        list.remove(20);
        list.printList(); // 15 -> 30 -> null

        System.out.println("[없는 값 50 삭제 시도]");
        list.remove(50); // 에러 메시지 출력되어야 함
    }
}
