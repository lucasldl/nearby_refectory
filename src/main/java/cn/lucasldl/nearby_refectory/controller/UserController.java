package cn.lucasldl.nearby_refectory.controller;

import cn.lucasldl.nearby_refectory.entity.Refectory;
import cn.lucasldl.nearby_refectory.service.RefectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private RefectoryService refectoryService;

    /**
     * 查询周围的餐厅
     * @param longitude 经度
     * @param latitude 维度
     * @param distance 范围(km)
     * @return
     */
    @RequestMapping("near")
    @ResponseBody
    public GeoResults<Refectory> near(@RequestParam("longitude") double longitude, @RequestParam("latitude") double latitude, @RequestParam("distance") double distance){
        GeoResults<Refectory> results = refectoryService.findNear(longitude, latitude, distance);
       return results;
    }

    /**
     * 根据id查找餐厅信息
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("{id}")
    @ResponseBody
    public String findById(Model model, @PathVariable("id") Long id){
        Refectory refectory = refectoryService.findRefectoryById(id);
        if(refectory != null){
            model.addAttribute("refectory", refectory);
            return "查询成功";
        }
        return "查询失败，请检查id";
    }
}
