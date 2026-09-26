# Project 1
## 0. 简介
本项目主要分为两部分，数据结构部分和应用部分。在数据结构部分，我分别使用双向循环链表和循环数组实现双端队列（Double Ended Queue，下简称Deque），并编写随机对比测试，检查两种实现的行为是否一致。在应用部分，我按照课程提供的算法和框架，使用双端队列完成吉他弦声音合成。此外，在Extra Credit（EC）中，我按照要求实现了一个简易autograder，通过与参考实现对比发现错误，并输出导致错误的操作序列。
## 1. 哪些部分体现了我的编程能力？
### 1.1. 根据接口需求从零完成数据结构实现
2021年版本的CS61B，project指引还相对简陋，[本项目的文档](https://sp21.datastructur.es/materials/proj/proj1/proj1#introduction) "will provide relatively little scaffolding. In other words, we’ll say what you should do, but not how."

文档只给了Deque的api，因此，我需要从创建类开始，自行组织内部数据结构、成员变量、辅助方法及各个接口的实现，而不是在已经搭建好的代码框架中完成部分代码。LinkedListDeque和ArrayDeque的代码由我自己编写完成，见`ArrayDeque.java`和`LinkedListDeque.java`

### 1.2. 循环数组的状态维护与resize
在ArrayDeque中，我使用循环数组存储元素，通过`nextFirst`、`nextLast`和`size`维护队列状态。比较tricky的是元素可能跨越数组末尾，队列中的逻辑位置与数组下标并不直接对应（例如在几次的双端插入删除后，逻辑上的“第一个元素”需要靠`nextFirst`去定位），因此需要通过取模运算处理下标回绕，并将访问位置转换为实际数组下标。

此外，当ArrayDeque底层的数组满了时，扩容时将容量翻倍以实现单次插入的均摊时间复杂度为$O(1)$，当容量不少于16，且删除后使用率低于25%时，容量将缩小一半。这既避免了每次插入都重新分配数组，也能在大量删除后释放多余空间。

扩缩容时，原数组中的元素可能分布在数组两端，不能直接按照实际数组下标顺序复制。我先找到逻辑上的“第一个元素”，然后按队列顺序将元素搬移到新数组，再重新设置`nextFirst`和`nextLast`，保证扩缩容前后的元素顺序一致。


### 1.3. LinkedListDeque
我认为LinkedListDeque中有两点可以体现我的编程能力，第一是双向循环链表的实现，包含基本的`addFirst`、`removeLast`、`get`等操作，插入和删除时，需要正确维护相邻节点的前后引用，并处理空队列、单元素队列等情况。在此基础上，我还实现了`iterator`和`equals`方法，支持逐个遍历元素，以及按元素内容和顺序比较不同的Deque实现。

第二是`getRecursive`方法，要求用递归的方式实现`get`功能，但是LinkedListDeque本身并不是递归的结构，不能直接在`getRecursive`里递归，我编写了一个接收当前节点和剩余步数的helper method。每次递归向后移动一个节点，并将剩余步数减一；剩余步数为零时返回当前元素，遇到哨兵节点则返回null，这个方法设为private并在`getRecursive`里调用，保持了对外接口的简洁。


### 1.4. 使用抽象
我通过接口`Deque<T>`统一两种队列实现的操作。在声音合成部分，`GuitarString.java`中通过该接口使用队列，使声音合成算法不依赖底层采用数组还是链表，用户只能通过规范的接口去使用，而内部的实现被我们隔离开了。
在MaxArrayDeque中，我通过继承已经实现的ArrayDeque，仅增加查找最大元素的功能，并通过Comparator支持不同的比较规则，减少重复代码。

## 2. 哪些部分体现了我的Debug能力？
