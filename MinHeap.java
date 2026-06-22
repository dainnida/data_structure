import java.util.*;

public class MinHeap {
    private ArrayList<Integer> heap = new ArrayList<>();

    // 생성자
    public MinHeap() {
        heap.add(0); // 인덱스를 1부터 쓰기 위함
    }

    // 삽입 : 일단 마지막 노드에 삽입 -> 부모 노드와 비교해서 교환
    public void push(int value) {
        heap.add(value);
        int idx = heap.size() - 1;

        // 부모가 자식보다 크다면
        while (idx > 1 && heap.get(idx / 2) > heap.get(idx)) {
            int tmp = heap.get(idx / 2);
            heap.set(idx / 2, heap.get(idx)); // 부모는 자식 값으로
            heap.set(idx, tmp); // 자식은 부모값으로

            idx /= 2;
        }
    }

    // 삭제 : 일단 루트 노드 삭제 후 가장 마지막 노드를 루트에 배치 -> 자식 노드와 비교해서 교환
    public int pop() {
        if (heap.size() < 2)
            return 0;

        // return할 때 쓰일 삭제할 값
        int deleteValue = heap.get(1);

        // 가장 마지막 노드를 루트에 배치
        heap.set(1, heap.get(heap.size() - 1));
        // 마지막 노드 삭제
        heap.remove(heap.size() - 1);

        int idx = 1;
        while (idx * 2 < heap.size()) { // 자식 노드가 존재할 때까지
            // 우선 왼쪽 자식을 가져옴
            int child = heap.get(idx * 2);
            int childIdx = idx * 2;
            // 만약 자식이 두 명이라면, 더 작은 자식과 비교해야 함. 무조건 자식보다 부모가 더 작아야 하므로
            if (idx * 2 + 1< heap.size() && heap.get(idx * 2 + 1) < child) {
                child = heap.get(idx * 2 + 1);
                childIdx = idx * 2 + 1;
            }

            // 부모가 자식보다 작으면 끝
            if (heap.get(idx) < child) {
                break;
            }

            // 아니면 자식과 교환
            int tmp = heap.get(idx);
            heap.set(idx, child); // 부모는 자식 값으로
            heap.set(childIdx, tmp); // 자식은 부모 값으로
            idx = childIdx; // 부모 인덱스를 자식 인덱스로 갱신
        }

        return deleteValue;
    }

    // 실행을 위한 main 메서드
    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();

        System.out.println("=== 힙에 데이터 삽입 ===");
        System.out.println("10, 5, 20, 3, 15 삽입 중...");
        minHeap.push(10);
        minHeap.push(5);
        minHeap.push(20);
        minHeap.push(3);
        minHeap.push(15);

        System.out.println("\n=== 힙에서 데이터 삭제 (최솟값부터 나와야 함) ===");
        System.out.println("pop: " + minHeap.pop()); // 3 출력되어야 함
        System.out.println("pop: " + minHeap.pop()); // 5 출력되어야 함
        System.out.println("pop: " + minHeap.pop()); // 10 출력되어야 함
        System.out.println("pop: " + minHeap.pop()); // 15 출력되어야 함
        System.out.println("pop: " + minHeap.pop()); // 20 출력되어야 함
        System.out.println("pop: " + minHeap.pop()); // 빈 힙이라 0 출력되어야 함
    }
}
