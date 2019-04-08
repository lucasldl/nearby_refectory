package cn.lucasldl.nearby_refectory.controller;

import cn.lucasldl.nearby_refectory.entity.Refectory;
import cn.lucasldl.nearby_refectory.service.RefectoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "普通用户接口")
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
    @ApiOperation("查询周围的餐厅")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longitude", value = "经度", required = true, paramType = "form", dataType = "double", example = "11.11"),
            @ApiImplicitParam(name = "latitude", value = "维度", required = true, paramType = "form", dataType = "double", example = "22.22"),
            @ApiImplicitParam(name = "distance", value = "范围(km)", required = true, paramType = "form", dataType = "double", example = "0.5")
    })
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
    @ApiOperation("根据id查询餐厅信息")
    @ApiImplicitParam(name = "id", value = "餐厅唯一标识", required = true, paramType = "form", dataType = "long", example = "2018031545")
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
