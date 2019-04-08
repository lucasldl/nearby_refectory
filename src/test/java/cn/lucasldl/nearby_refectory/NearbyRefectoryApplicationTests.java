package cn.lucasldl.nearby_refectory;

import cn.lucasldl.nearby_refectory.entity.Refectory;
import cn.lucasldl.nearby_refectory.service.RefectoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.GeoResults;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NearbyRefectoryApplicationTests {
	@Autowired
	private RefectoryService refectoryService;

	@Test
	public void addRefectory(){
		Refectory refectory = new Refectory();
		double[] location = {105.941387,29.356794};
		refectory.setTitle("佈麻佈辣")
				.setLocation(location)
				.setIntroduce("主营炒菜")
				.setAddress("永川")
				.setPhone("13131313131");
		System.out.println(refectoryService.addRefectory(refectory));
	}

	@Test
	public void delRefectory(){
		refectoryService.delRefectoryById(20190403221950L);
	}

	@Test
	public void getRefectory(){
		System.out.println(refectoryService.findRefectoryById(20190403174748L));
	}

	@Test
	public void getRefectoryList(){
		System.out.println(refectoryService.findRefectoryByName("面").size());
	}

	@Test
	public void updateRefectory(){
		Refectory refectory = new Refectory();
		double[] location = {105.946803,29.357331};
		refectory.setId(20190403174312L)
				.setLocation(location)
				.setTitle("米线")
				.setIntroduce("主营米线d")
				.setPhone("13154545")
				.setAddress("永川");
		System.out.println(refectoryService.updateRefectoryById(refectory));
	}

	@Test
	public void findAll(){
		System.out.println(refectoryService.findAll(10, 1));
	}

	@Test
	public void findNear(){
		GeoResults<Refectory> list = refectoryService.findNear(105.944751,29.360926, 0.5);
		System.out.println(list.getContent().size());
		System.out.println(refectoryService.findNear(105.944751,29.360926, 0.5));
	}
}
