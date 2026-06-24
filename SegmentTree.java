public class SegmentTree {
    
    long[] tree;
    int n; // 원본 배열의 크기

    // 생성자(tree 초기화)
    public SegmentTree(int n) {
        this.n = n;
        this.tree = new long[4 * n];
    }

    // tree[node]에 tree[start]부터 tree[end]까지의 합 저장하기
    public long init(long[] arr, int node, int start, int end) {
        if (start == end) // 더 이상 쪼갤 수 없으므로 원본값 채우기
            return tree[node] = arr[start];

        // 자식들을 더한 게 부모의 값
        int mid = (start + end) / 2;
        return tree[node] = init(arr, node * 2, start, mid) + init(arr, node * 2 + 1, mid + 1, end);
    }

    // left ~ right: 합을 구하고자 하는 범위
    public long sum(int node, int start, int end, int left, int right) {
        // 1. 찾고자 하는 범위에 포함되지 않음
        if (left > end || right < start)
            return 0;

        // 2. 나의 모두가 찾고자 하는 범위에 포함됨
        if (left <= start && end <= right)
            return tree[node];

        // 3. 나의 일부만 찾고자 하는 범위에 포함됨
        // -> 반씩 쪼개서 포함되지 않은 값은 거른 다음에 더한 게 답
        int mid = (start + end) / 2;
        return sum(node * 2, start, mid, left, right) + sum(node * 2 + 1, mid + 1, end, left, right);
    }

    // // 사용자가 main에서 편하게 호출할 수 있도록 감싸주는 메서드
    // public long query(int left, int right) {
    //     // 꼭대기 노드(1번)부터 전체 구간(0 ~ n-1)을 탐색하라고 내부 sum을 호출
    //     return this.sum(1, 0, this.n - 1, left, right);
    // }

    // 실행을 위한 main 메서드
    public static void main(String[] args) {
        // 1. 테스트용 원본 배열 생성 (인덱스: 0, 1, 2, 3)
        long[] arr = {1, 2, 5, 3};
        System.out.println("원본 배열: [1, 2, 5, 3]");
        System.out.println("--------------------------------");

        // 2. 세그먼트 트리 객체 생성 및 초기화(Build)
        SegmentTree segTree = new SegmentTree(arr.length);
        segTree.init(arr, 1, 0, arr.length - 1);

        // 3. 다양한 구간 합 테스트
        // 테스트 1: 인덱스 1부터 3까지의 합 (2 + 5 + 3 = 10)
        long ans1 = segTree.sum(1, 0, arr.length - 1, 1, 3);
        System.out.println("인덱스 1 ~ 3 까지의 합 (예상값: 10) -> 결과: " + ans1);

        // 테스트 2: 인덱스 0부터 2까지의 합 (1 + 2 + 5 = 8)
        long ans2 = segTree.sum(1, 0, arr.length - 1, 0, 2);
        System.out.println("인덱스 0 ~ 2 까지의 합 (예상값: 8)  -> 결과: " + ans2);

        // 테스트 3: 인덱스 2부터 2까지의 합 (자기 자신 = 5)
        long ans3 = segTree.sum(1, 0, arr.length - 1, 2, 2);
        System.out.println("인덱스 2 ~ 2 까지의 합 (예상값: 5)  -> 결과: " + ans3);

        // 테스트 4: 전체 구간 합 (1 + 2 + 5 + 3 = 11)
        long ans4 = segTree.sum(1, 0, arr.length - 1, 0, 3);
        System.out.println("인덱스 0 ~ 3 까지의 합 (예상값: 11) -> 결과: " + ans4);
    }
}
