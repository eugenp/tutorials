package org.springframework.tutorial.tutorial3.XMLbased;

/**
 * 通过工厂方法创建Bean
 * @author lihongjie
 *
 */
public class Stage {

	private Stage() {
		
	}
	
	// 延迟加载实例 initialization on demand holder
	private static class StageSingletonHolder  {
		static Stage instance = new Stage();
	}
	
	// 返回实例
	public static Stage getInstance() {
		return StageSingletonHolder.instance;
	}
	
	public void perform() {
		System.out.println("Stage ...");
	}
}
