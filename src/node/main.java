package node;

import java.util.HashSet;
import java.util.Set;

/**
 * @author linyw
 */
public class main {
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode cur = dummyHead;
        while (cur.next != null) {
            if (cur.next.val == val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return dummyHead.next;
    }

    public ListNode reverseList(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //先遍历到tail节点，测量链表长度
        int length = 0;
        ListNode cur = head;
        while(cur != null) {
            length++;
            cur = cur.next;
        }
        int index = length - n;
        cur = head;
        ListNode pre = null;
        if (index == 0) {
            return cur.next;
        } else {
            while(index > 0) {
                pre = cur;
                cur = cur.next;
                index--;
            }
            pre.next = cur.next;
            return head;
        }
    }
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //求链表长度
        int lengthA = getLength(headA);
        int lengthB = getLength(headB);
        if (lengthA < lengthB) {
            ListNode temp = headB;
            headB = headA;
            headA = temp;
        }
        int dif = Math.abs(lengthA - lengthB);
        while (dif > 0) {
            headA = headA.next;
            dif--;
        }
        while (headA != null) {
            if (headA == headB) {
                return headA;
            } else {
                headA = headA.next;
                headB = headB.next;
            }
        }
        return null;
    }

    /**
     * @param head
     * @return
     */
    private int getLength(ListNode head) {
        int lenth = 0;
        while (head != null) {
            lenth++;
            head = head.next;
        }
        return lenth;
    }

    /**
     * 给你一个链表的头节点 head ，判断链表中是否有环。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。注意：pos 不作为参数进行传递 。仅仅是为了标识链表的实际情况。
     * 如果链表中存在环 ，则返回 true 。 否则，返回 false 。
     * 示例 1：
     *
     *
     *
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：true
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     */
    public boolean hasCycle(ListNode head) {
        //快慢指针
        ListNode slow = head;
        ListNode fast = head;
        while(slow != null && fast != null) {
            slow = slow.next;
            fast = fast.next == null ? null : fast.next.next;
            if (slow ==  fast && slow != null) {
                return true;
            }
        }
        return false;
    }
    /**
     * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     * 不允许修改 链表。
     * 示例 1：
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：返回索引为 1 的链表节点
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     */
    public ListNode detectCycle(ListNode head) {
        //用set记录节点
        Set<ListNode> set = new HashSet<>();
        ListNode cur = head;
        while (cur != null) {
            if (set.contains(cur)) {
                return cur;
            } else {
                set.add(cur);
                cur = cur.next;
            }
        }
        return null;
    }

}

  class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

