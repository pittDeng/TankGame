坦克大战
====
画坦克
------
* 画出两个轮子
* 画出转向器
* 画出圆盘

_子弹逻辑（Version2.0）_
-------
* 每次按J键时调用Tank.shoot()方法，生成一个新的子弹，但是每秒钟只能生成2颗子弹
* 每次生成一颗子弹后，都要在子弹队列中检查该子弹是否还活着，如果没有将子弹从Vector<Bullet> bullets中remove掉。
* 因为添加了子弹不能像原来那样只在按键时调用mp.repaint(),要形成子弹能够运动，就必须每隔一段时间调用mp.repaint()，因此就要将MyPanel类实现Runnable接口，这样就可以使用线程来控制。

