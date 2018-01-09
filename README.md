从windows层进行修改来实现在4.4以上的版本上改变statusbar和navigationbar颜色的目的（有些人称作沉浸式）。
不需要对布局进行任何修改，使用方式
1.把translib下的TransHelper复制到你当前项目中
2.在setcontentview之后设置new TransHelper(MainActivity.this).transContent(getResources().getColor(R.color.statusBarColor));

注意：
activity的主题需要时noactionbar或者其子类,否则系统会自动添加一个状态栏高度的头部


效果
![](https://github.com/LiuLinXin/TransparencyFromWindows/blob/master/trans.png)
