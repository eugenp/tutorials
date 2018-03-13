var originalClassName;//最开始的类名
var originalMenuClassName;//最开始的菜单类名
var lastSelectedMenu = null; //最后一次选择菜单

function highlightCategoryBar(sender,verb) //分类菜单栏的函数
{
	var subMenuItems = sender.nextSibling.children[0].children[0];
	if(verb == "over")
	{
		originalClassName = sender.className;
		sender.className = "navi_Category_selected";
	}
	else
	{
		if(subMenuItems.style.display == "none")
		{
			sender.className = "navi_Category_Unselected";
			
		}
	}
}
function switchCategoryBar() //改变分类的菜单栏
{
	
}

function highlightMenuBar()//子菜单
{
	
}
function switchMenuBar()
{
	
}