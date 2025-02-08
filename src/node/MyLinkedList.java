package node;

import org.junit.jupiter.api.Test;

/**
 * @author linyw
 */
public class MyLinkedList {
    /**
     * 实现 MyLinkedList 类：
     *
     * MyLinkedList() 初始化 MyLinkedList 对象。
     * int get(int index) 获取链表中下标为 index 的节点的值。如果下标无效，则返回 -1 。
     * void addAtHead(int val) 将一个值为 val 的节点插入到链表中第一个元素之前。在插入完成后，新节点会成为链表的第一个节点。
     * void addAtTail(int val) 将一个值为 val 的节点追加到链表中作为链表的最后一个元素。
     * void addAtIndex(int index, int val) 将一个值为 val 的节点插入到链表中下标为 index 的节点之前。如果 index 等于链表的长度，那么该节点会被追加到链表的末尾。如果 index 比长度更大，该节点将 不会插入 到链表中。
     * void deleteAtIndex(int index) 如果下标有效，则删除链表中下标为 index 的节点。
     */
    private final ListNode dummyHead;
    private int size;
    public MyLinkedList() {
        dummyHead = new ListNode();
        size = 0;
    }

    public int get(int index) {
        ListNode node = getListNodeByIndex(index);
        return node == null ? -1 : node.val;
    }

    public void addAtHead(int val) {
        ListNode old = dummyHead.next;
        ListNode newNode = new ListNode(val);
        //dummy指向new
        dummyHead.next = newNode;
        //new指向old
        newNode.next = old;
        size++;
    }

    public void addAtTail(int val) {
        ListNode node = new ListNode(val);
        ListNode pre = size - 1 < 0 ? dummyHead : getListNodeByIndex(size - 1);
        pre.next = node;
        size++;
    }

    public void addAtIndex(int index, int val) {
        ListNode pre = index == 0 ? dummyHead : getListNodeByIndex(index - 1);
        ListNode newNode = new ListNode(val);
        //如果pre为空，则说明index不合法，不执行插入
        //如果pre不为空，把new插入的pre的后面，让new的next指向pre原先的后序节点
        if (pre != null) {
            ListNode next = pre.next;
            pre.next = newNode;
            newNode.next = next;
            size++;
        }
    }

    public void deleteAtIndex(int index) {
        //获取前序执行删除操作
        ListNode pre = index == 0 ? dummyHead : getListNodeByIndex(index - 1);
        //如果pre为空，说明输入不合法
        //如果pre不为空，把pre的next指向要删除节点的下一个节点
        if (pre != null) {
            ListNode target = pre.next;
            if (target != null) {
                pre.next = target.next;
                size--;
            }
        }
    }

    private ListNode getListNodeByIndex(int index) {
        ListNode cur = dummyHead;
        while (index >= 0 && cur != null) {
            cur = cur.next;
            index--;
        }
        return cur;
    }


    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    @Test
    public void test() {
        MyLinkedList obj = new MyLinkedList();
        int param_1 = obj.get(0);
        System.out.println(param_1);
    }
}
