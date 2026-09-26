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
### 2.1. 测试
先通过测试检测问题，也可以顺便定位到需要debug的位置，本项目的`LinkedListDequeTest.java`就对 LinkedListDeque 和 ArrayDeque 进行了测试，其中最后的随机测试以及ArrayDeque部分的测试是我写的，随机对比测试让 ArrayDeque 和 LinkedListDeque 执行相同的随机操作，并比较队列大小及相关操作的返回值。
相比只检查几个手动构造的例子，随机测试能够检查更多操作组合，有助于发现连续操作后出现的错误。

此外，本项目的EC中，我实现了一个与参考实现对比发现错误的autograder，为了让测试失败后能获得具体的排查线索，我使用 StringBuilder 持续记录实际执行的操作及其参数，并将操作序列作为断言失败时的提示信息。这样不仅能够发现结果不一致，还能知道经过哪些操作后出现了错误。

下面举例做project过程中遇到的问题以及如何解决的
### 2.2 构造函数
在测试LinkedListDeque时，测试失败，而且连实际得到的是什么都没有打印出来，对写的测试进行调试，发现创建LinkedListDeque时，并没有按照构造函数的要求让`sentinel`指向新建的Node，而是直接变成了null，在创建LinkedListDeque处打断点，step into后发现并未进入我写的构造函数，所以定位到构造函数出现问题，后来发现构造函数的signature写成了`public void LinkedListDeque()`，于是去掉void

去掉void后，在创建LinkedListDeque处打断点调试，先step over看构造函数是否成功修复，成功创建了sentinel，但是它指向的Node的 `prev` 和 `next` 都是null而没有指向自己，再次step into进入Node的构造函数，发现传入的 `prev` 和 `next` 都是null而不是sentinel，于是定位到构造函数的参数写错，发现原本写的是 `sentinel = new Node(null, sentinel, sentinel);` ，在创建Node时sentinel还没被赋值，所以默认是null，修改后问题解决。
