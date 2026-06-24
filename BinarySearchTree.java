public class BinarySearchTree {

    private static class Node {
        int data;
        Node left;
        Node right;

        public Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root = null; // 첫 번째 노드를 가리키는 포인터

    // 삽입
    public void add(int value) {
        Node newNode = new Node(value);

        // 1. 반복문으로 구현
        // 첫 노드라면
        if (root == null) {
            root = newNode;
            return;
        }

        Node currNode = root;
        while (true) {
            // 부모보다 작은 값이면 왼쪽으로
            if (currNode.data > value) {
                if (currNode.left == null) {
                    currNode.left = newNode;
                    break;
                }
                currNode = currNode.left;
            }
            // 부모보다 큰 값이면 오른쪽으로
            else if (currNode.data < value) {
                if (currNode.right == null) {
                    currNode.right = newNode;
                    break;
                }
                currNode = currNode.right;
            }
            // 같은 경우에는 중복 불가
            else {
                System.out.println("중복된 값입니다.");
                break;
            }
        }
        // 2. 재귀로 구현 (첫 노드라도 root = newNode로 잘 됨)
        // root = addRecursive(root, newNode);
    }

    // 2. 재귀로 삽입 구현
    private Node addRecursive(Node currNode, Node newNode) {
        if (currNode == null) {
            currNode = newNode;
            return newNode; // 빈자리에 새 거 넣기
        }
        
        // 부모보다 작은 값이면 왼쪽으로
        if (currNode.data > newNode.data)
            currNode.left = addRecursive(currNode.left, newNode);
        // 부모보다 큰 값이면 오른쪽으로
        else if (currNode.data < newNode.data)
            currNode.right = addRecursive(currNode.right, newNode);
        // 같은 경우에는 중복 불가
        else {
            System.out.println("중복된 값입니다.");
        }
        return currNode; // 빈자리 아니면 기존 노드 그대로 넣기
    }

    // 검색
    public boolean find(int value) {
        // 1. 반복문으로 구현
        Node currNode = root;
        while (currNode != null) {
            // 부모보다 작은 값이면 왼쪽으로
            if (currNode.data > value)
                currNode = currNode.left;
            // 부모보다 큰 값이면 오른쪽으로
            else if (currNode.data < value)
                currNode = currNode.right;
            // 같음 = 찾음!
            else
                return true;
        }
        // 못 찾음
        return false;

        // 2. 재귀로 구현
        // return findRecursive(root, value);
    }

    private boolean findRecursive(Node currNode, int value) {
        // 못 찾음
        if (currNode == null)
            return false;

        // 찾음
        if (currNode.data == value)
            return true;
        
        // 부모보다 작은 값이면 왼쪽으로
        if (currNode.data > value)
            return findRecursive(currNode.left, value);
        
        // 부모보다 큰 값이면 오른쪽으로
        else
            return findRecursive(currNode.right, value);
    }

    // 삭제
    public int remove(int value) {
        if (!find(value)) {
            System.out.println("존재하지 않는 값입니다");
            return -1;
        }

        // 먼저 삭제할 노드를 찾는다
        Node prevNode = null; // root의 부모는 없음
        Node currNode = root;
        boolean isLeftChild = false;

        while (currNode.data != value) {
            prevNode = currNode;
            // 부모보다 작은 값이면 왼쪽으로
            if (currNode.data > value) {
                currNode = currNode.left;
                isLeftChild = true;
            }
            // 부모보다 큰 값이면 오른쪽으로
            else {
                currNode = currNode.right;
                isLeftChild = false;
            }
        }

        // 1. 삭제할 노드의 자식이 없으면
        // 그냥 삭제만 하면 됨
        if (currNode.left == null && currNode.right == null) {
            // 삭제할 노드가 루트 하나뿐
            if (currNode == root) {
                root = null;
            }
            else if (isLeftChild)
                prevNode.left = null;
            else
                prevNode.right = null;
            // 삭제된 노드는 GC가 알아서 삭제해 줌
        }

        // 2. 삭제할 노드의 자식이 하나면
        // 전 노드를 자식과 연결해주면 됨
        // 2-1) 오른쪽 자식만 있음
        else if (currNode.left == null) {
            // 삭제할 노드가 루트임
            if (currNode == root) {
                root = currNode.right;
            }
            else if (isLeftChild)
                prevNode.left = currNode.right;
            else
                prevNode.right = currNode.right;
        }
        // 2-2) 왼쪽 자식만 있음
        else if (currNode.right == null) {
            // 삭제할 노드가 루트임
            if (currNode == root) {
                root = currNode.left;
            }
            else if (isLeftChild)
                prevNode.left = currNode.left;
            else
                prevNode.right = currNode.left;
        }

        // 3. 삭제할 노드의 자식이 둘임
        // 1) 왼쪽 자식 중에서 가장 큰(가장 오른쪽)에 있는 노드 혹은
        // 2) 오른쪽 자식 중에서 가장 작은(가장 왼쪽)에 있는 노드와 교환하면 됨.
        // 그 노드가 삭제할 노드와 가장 가까운 값이기 때문
        // 여기선 1)로 구현하겠음
        // 그러나! 교환할 노드에게 오른쪽 자식이 없는 것은 확실하지만 왼쪽 자식이 있을 수도 있음
        // 그러므로 왼쪽 자식과 교환할 노드의 바로 전 노드(부모)와 연결해주어야 함
        else {
            // 3-1. 왼쪽 자식 중에서 가장 큰(가장 오른쪽)에 있는 노드 찾기
            Node exchangeNode = currNode.left;
            Node exchangePrevNode = currNode;
            while (exchangeNode.right != null) {
                exchangePrevNode = exchangeNode;
                exchangeNode = exchangeNode.right;
            }

            // 3-2. 삭제할 자리에 교환할 노드의 값 넣기
            currNode.data = exchangeNode.data;

            // 3-3. 교환할 노드의 왼쪽 자식을 교환할 전 노드(교환할 노드의 부모)의 오른쪽 자식에 연결해주기
            // 3-3-1) 삭제할 노드의 바로 왼쪽 노드가 교환할 노드임
            if (exchangePrevNode == currNode)
                exchangePrevNode.left = exchangeNode.left;
            // 3-3-2) 삭제할 노드와 교환할 노드 사이에 다른 노드가 있음
            else
                exchangePrevNode.right = exchangeNode.left;
        }

        return value;
    }

    // 삭제 (2. 재귀로 구현)
    public int removeRecursive (int value) {
        if (!find(value)) {
            System.out.println("존재하지 않는 값입니다");
            return -1;
        }

        root = removeNode(root, value);

        return value;
    }

    private Node removeNode(Node currNode, int value) {
        // 못 찾음
        if (currNode == null) {
            return null; // 빈자리엔 그대로 비어야 함
        }

        // 찾음
        if (currNode.data == value) {
            // 1. 삭제할 노드의 자식이 없거나 하나임
            if (currNode.left == null)
                // 삭제할 노드의 오른쪽 자식을 삭제 노드의 부모에 연결해주기(삭제 노드는 연결 끊김)
                return currNode.right;
            if (currNode.right == null)
                // 삭제할 노드의 왼쪽 자식을 삭제 노드의 부모에 연결해주기(삭제 노드는 연결 끊김)
                return currNode.left;

            // 2. 삭제할 노드의 자식이 둘임
            // 2-1. 먼저 교환할 노드 즉, 왼쪽 자식 중에서 가장 큰(가장 오른쪽)에 있는 노드 찾기
            Node exchangeNode = currNode.left;
            while (exchangeNode.right != null) {
                exchangeNode = exchangeNode.right;
            }

            // 2-2. 삭제할 자리에 교환할 노드의 값 넣기
            currNode.data = exchangeNode.data;

            //2-3. 교환한 노드 삭제하기
            currNode.left = removeNode(currNode.left, exchangeNode.data);

            return currNode; // 교환 작업을 완료한 현재 노드를 부모 노드와 연결
        }
        
        // 부모보다 작은 값이면 왼쪽으로
        if (currNode.data > value)
            currNode.left = removeNode(currNode.left, value);
        
        // 부모보다 큰 값이면 오른쪽으로
        else
            currNode.right = removeNode(currNode.right, value);

        return currNode; // 기존 노드를 부모 노드와 연결
    }

    // 중위 순회
    public void printInOrder() {
        printNode(root);
    }

    private void printRecursive(Node currNode) {
        if (currNode == null) // 끝
            return;
        printRecursive(currNode.left); // 왼쪽 자식 다 출력
        System.out.print(currNode.data + " "); // 부모 출력
        printRecursive(currNode.right); // 오른쪽 자식 다 출력
    }

    // 실행을 위한 main 메서드
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        System.out.println("=== 1. 데이터 삽입 (add) ===");
        // 일부러 뒤죽박죽 넣어도 중위 순회하면 정렬되어야 합니다.
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(45);
        bst.add(60);
        bst.add(80);
        bst.printTree();

        System.out.println("\n=== 2. 중복 삽입 예외 테스트 ===");
        bst.add(40); // 중복 경고 메시지가 떠야 함

        System.out.println("\n=== 3. 중위 순회 출력 (printInOrder) ===");
        System.out.print("트리 출력 결과 (오름차순): ");
        bst.printInOrder(); // 예상 결과: 20 30 40 45 50 60 70 80 

        System.out.println("\n=== 4. 데이터 검색 (find) ===");
        System.out.println("트리에 40이 있나요? " + bst.find(40));  // true
        System.out.println("트리에 99가 있나요? " + bst.find(99));  // false

        System.out.println("\n=== 5. 데이터 삭제 (remove) ===");
        System.out.println("=== 원본 트리 ===");
        bst.printTree();

        System.out.println("\n=== 5-1. 말단 노드(20) 삭제 ===");
        // bst.remove(20);
        bst.removeRecursive(20);
        bst.printTree();

        System.out.println("\n=== 5-2. 자식이 하나인 노드(30) 삭제 ===");
        // bst.remove(30);
        bst.removeRecursive(30);
        bst.printTree();

        System.out.println("\n=== 5-3. 자식이 둘인데 왼쪽 자식 하나만 존재하는 노드(70) 삭제 ===");
        // bst.remove(70);
        bst.removeRecursive(70);
        bst.printTree();

        System.out.println("\n=== 5-4. 루트 노드(50) 삭제면서 자식이 둘인데 그 사이에 노드가 있는 경우 ===");
        // bst.remove(50);
        bst.removeRecursive(50);
        bst.printTree();
    }

    // 트리의 실제 구조를 눈으로 보여주는 시각화 메서드
    public void printTree() {
        printTreeStructure(root, "", true);
    }

    private void printTreeStructure(Node currNode, String prefix, boolean isLeft) {
        if (currNode == null) return;

        // 1. 오른쪽 자식을 먼저 출력 (화면 위쪽에 배치됨)
        printTreeStructure(currNode.right, prefix + (isLeft ? "│   " : "    "), false);

        // 2. 현재 노드 출력
        System.out.println(prefix + (isLeft ? "└── " : "┌── ") + currNode.data);

        // 3. 왼쪽 자식을 출력 (화면 아래쪽에 배치됨)
        printTreeStructure(currNode.left, prefix + (isLeft ? "    " : "│   "), true);
    }
}
