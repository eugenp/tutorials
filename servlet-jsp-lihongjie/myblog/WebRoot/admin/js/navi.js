var originalClassName;//�ʼ������
var originalMenuClassName;//�ʼ�Ĳ˵�����
var lastSelectedMenu = null; //���һ��ѡ��˵�

function highlightCategoryBar(sender,verb) //����˵����ĺ���
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
function switchCategoryBar() //�ı����Ĳ˵���
{
	
}

function highlightMenuBar()//�Ӳ˵�
{
	
}
function switchMenuBar()
{
	
}