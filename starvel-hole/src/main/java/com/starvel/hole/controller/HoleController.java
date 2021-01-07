package com.starvel.hole.controller;

import com.starvel.common.result.Result;
import com.starvel.hole.service.HoleService;
import com.starvel.hole.service.HoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by skyyemperor on 2020-12-22 23:15
 * Description :
 */
@RestController
@RequestMapping("/api/hole")
public class HoleController {

    @Autowired
    private HoleUserService holeUserService;

    @Autowired
    private HoleService holeService;

    /**
     * @api {post} /api/hole/stoken/generate STOKEN生成
     * @apiVersion 0.0.1
     * @apiName GenerateSToken
     * @apiGroup Hole
     * @apiDescription STOKEN生成
     * @apiHeader {String} TOKEN 系统的用户TOKEN，通过系统登录获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "TOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": {
     * "stoken": "9C7B5EA64036265886B3C2A236205F98D-352209"
     * }
     * }
     */
    @PostMapping("/stoken/generate")
    public Result generateSToken(@RequestParam("_uid_") Long userId) {
        return holeUserService.generateSToken(userId);
    }


//    @PostMapping("/stoken/validate")
//    public Result validateStoken(@RequestParam String stoken) {
//        return holeUserService.validateStoken(stoken);
//    }

    /**
     * @api {post} /api/hole/post 发布树洞
     * @apiVersion 0.0.1
     * @apiName PostHole
     * @apiGroup Hole
     * @apiDescription 发布树洞
     * @apiHeader {String} STOKEN HOLE系统的用户Token，通过/api/hole/stoken/generate获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "STOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {Long} parentId 父树洞，若是新开树洞则为0
     * @apiParam {String} content 内容
     * @apiParam {String} [type=0] 类型
     * @apiParamExample {json} Request-Example:
     * {
     * "parentId":27623183,
     * "content":"这是树洞的内容",
     * "type":0
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": {
     * "hole": {
     * "holeId": 1,
     * "rootId": 0,
     * "parentId": 0,
     * "holeUserId": 3,
     * "content": "test!",
     * "date": "2020-12-29 16:23:07",
     * "like": 0,
     * "hate": 0,
     * "status": 0,
     * "type": 0
     * },
     * "holePoster": {
     * "holeUserId": 3,
     * "holeUserName": "南瓜",
     * "status": 0
     * }
     * }
     * }
     */
    @PostMapping("/post")
    public Result postHole(@RequestParam("_huid_") Long holeUserId,
                           @RequestParam Long parentId,
                           @RequestParam String content,
                           @RequestParam(defaultValue = "0") Integer type) {
        return holeService.postHole(holeUserId, parentId, content, type);
    }


    /**
     * @api {get} /api/hole/info 获取树洞信息
     * @apiVersion 0.0.1
     * @apiName GetHoleInfo
     * @apiGroup Hole
     * @apiDescription 获取树洞信息
     * @apiHeader {String} STOKEN HOLE系统的用户Token，通过/api/hole/stoken/generate获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "STOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {Long} holeId 树洞ID
     * @apiParamExample {json} Request-Example:
     * {
     * "holeId":27730001
     * }
     * @apiSuccess {Long} holeId 树洞唯一递增ID
     * @apiSuccess {Long} rootId 根树洞ID
     * @apiSuccess {Long} parentId 父树洞ID
     * @apiSuccess {Long} holeUserId 洞主ID
     * @apiSuccess {String} content 树洞内容
     * @apiSuccess {Long} date 发布时间的时间戳
     * @apiSuccess {Integer} like 点赞数
     * @apiSuccess {Integer} status 状态，0为默认，这个属性留着以后拓展
     * @apiSuccess {Integer} type 类型，0为默认，1为顶置..这个属性留着以后拓展
     * @apiSuccess {Object} holePoster 树洞用户
     * @apiSuccess {Long} holeUserId 树洞用户ID
     * @apiSuccess {String} holeUserName 树洞用户随机名称
     * @apiSuccess {Integer} status 树洞用户状态，不用管，留之后扩展
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": {
     * "hole": {
     * "holeId": 1,
     * "rootId": 0,
     * "parentId": 0,
     * "holeUserId": 3,
     * "content": "test!",
     * "date": "2020-12-29 16:23:07",
     * "like": 0,
     * "hate": 0,
     * "status": 0,
     * "type": 0
     * },
     * "holePoster": {
     * "holeUserId": 3,
     * "holeUserName": "南瓜",
     * "status": 0
     * }
     * }
     * }
     */
    @GetMapping("/info")
    public Result getHoleInfo(@RequestParam Long holeId) {
        return holeService.getHoleInfo(holeId);
    }

    /**
     * @api {post} /api/hole/like 点赞或取消点赞
     * @apiVersion 0.0.1
     * @apiName LikeHole
     * @apiGroup Hole
     * @apiDescription 点赞或取消点赞，若没有点过赞，则点赞；若已点赞，则为取消点赞
     * @apiHeader {String} STOKEN HOLE系统的用户Token，通过/api/hole/stoken/generate获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "STOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {Long} holeId 树洞ID
     * @apiParamExample {json} Request-Example:
     * {
     * "holeId":27730001
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success"
     * }
     */
    @PostMapping("/like")
    public Result likeHole(@RequestParam("_huid_") Long holeUserId,
                           @RequestParam Long holeId) {
        return holeService.likeHole(holeUserId, holeId);
    }

    /**
     * @api {get} /api/hole/has/like 是否点过赞
     * @apiVersion 0.0.1
     * @apiName IfLikeHole
     * @apiGroup Hole
     * @apiDescription 是否点过赞, 是返回true，否返回false
     * @apiHeader {String} STOKEN HOLE系统的用户Token，通过/api/hole/stoken/generate获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "STOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {Long} holeId 树洞ID
     * @apiParamExample {json} Request-Example:
     * {
     * "holeId":27730001
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": true
     * }
     */
    @GetMapping("/has/like")
    public Result hasLikeHole(@RequestParam("_huid_") Long holeUserId,
                              @RequestParam Long holeId) {
        return holeService.hasLikeHole(holeUserId, holeId);
    }

    /**
     * @api {post} /api/hole/hate 点踩或取消点踩
     * @apiVersion 0.0.1
     * @apiName HateHole
     * @apiGroup Hole
     * @apiDescription 点踩或取消点踩，若没有点过踩，则点踩；若已点踩，则为取消点踩
     * @apiHeader {String} STOKEN HOLE系统的用户Token，通过/api/hole/stoken/generate获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "STOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {Long} holeId 树洞ID
     * @apiParamExample {json} Request-Example:
     * {
     * "holeId":27730001
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success"
     * }
     */
    @PostMapping("/hate")
    public Result hateHole(@RequestParam("_huid_") Long holeUserId,
                           @RequestParam Long holeId) {
        return holeService.hateHole(holeUserId, holeId);
    }

    /**
     * @api {get} /api/hole/has/hate 是否点过踩
     * @apiVersion 0.0.1
     * @apiName IfHateHole
     * @apiGroup Hole
     * @apiDescription 是否点过踩, 是返回true，否返回false
     * @apiHeader {String} STOKEN HOLE系统的用户Token，通过/api/hole/stoken/generate获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "STOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {Long} holeId 树洞ID
     * @apiParamExample {json} Request-Example:
     * {
     * "holeId":27730001
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": true
     * }
     */
    @GetMapping("/has/hate")
    public Result hasHateHole(@RequestParam("_huid_") Long holeUserId,
                              @RequestParam Long holeId) {
        return holeService.hasHateHole(holeUserId, holeId);
    }

    /**
     * @api {get} /api/hole/list 获取hole列表
     * @apiVersion 0.0.1
     * @apiName GetHoleList
     * @apiGroup Hole
     * @apiDescription 根据rootId属性获取hole列表
     * @apiHeader {String} STOKEN HOLE系统的用户Token，通过/api/hole/stoken/generate获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "STOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {Long} holeId 树洞ID，若为0，则返回最新hole
     * @apiParam {Integer} [page=1] 页码
     * @apiParam {Integer} [count=10] 每页数目
     * @apiParamExample {json} Request-Example:
     * {
     * "holeId":27730001,
     * "page": 3,
     * "count": 10
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": [
     * {
     * "hole": {
     * "holeId": 1,
     * "rootId": 0,
     * "parentId": 0,
     * "holeUserId": 3,
     * "content": "test!",
     * "date": "2020-12-29 16:23:07",
     * "like": 0,
     * "hate": 1,
     * "status": 0,
     * "type": 0
     * },
     * "holeUser": {
     * "holeUserId": 3,
     * "holeUserName": "南瓜",
     * "status": 0
     * }
     * },
     * {
     * "hole": {
     * "holeId": 5,
     * "rootId": 1,
     * "parentId": 1,
     * "holeUserId": 3,
     * "content": "回复222",
     * "date": "2020-12-29 17:42:20",
     * "like": 0,
     * "hate": 0,
     * "status": 0,
     * "type": 0
     * },
     * "holeUser": {
     * "holeUserId": 3,
     * "holeUserName": "南瓜",
     * "status": 0
     * }
     * }
     * ]
     * }
     */
    @GetMapping("/list")
    Result getHoleList(@RequestParam Long holeId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer count) {
        return holeService.getHoleList(holeId, page, count);
    }

    /**
     * @api {get} /api/hole/reply 获取hole回复
     * @apiVersion 0.0.1
     * @apiName GetHoleReply
     * @apiGroup Hole
     * @apiDescription 根据parentId属性获取hole回复
     * @apiHeader {String} STOKEN HOLE系统的用户Token，通过/api/hole/stoken/generate获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "STOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {Long} holeId 树洞ID
     * @apiParam {Integer} [page=1] 页码
     * @apiParam {Integer} [count=10] 每页数目
     * @apiParamExample {json} Request-Example:
     * {
     * "holeId":27730001,
     * "page": 3,
     * "count": 10
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": [
     * {
     * "hole": {
     * "holeId": 1,
     * "rootId": 0,
     * "parentId": 0,
     * "holeUserId": 3,
     * "content": "test!",
     * "date": "2020-12-29 16:23:07",
     * "like": 0,
     * "hate": 1,
     * "status": 0,
     * "type": 0
     * },
     * "holeUser": {
     * "holeUserId": 3,
     * "holeUserName": "南瓜",
     * "status": 0
     * }
     * },
     * {
     * "hole": {
     * "holeId": 5,
     * "rootId": 1,
     * "parentId": 1,
     * "holeUserId": 3,
     * "content": "回复222",
     * "date": "2020-12-29 17:42:20",
     * "like": 0,
     * "hate": 0,
     * "status": 0,
     * "type": 0
     * },
     * "holeUser": {
     * "holeUserId": 3,
     * "holeUserName": "南瓜",
     * "status": 0
     * }
     * }
     * ]
     * }
     */
    @GetMapping("/reply")
    Result getHoleReply(@RequestParam Long holeId,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer count) {
        return holeService.getHoleReply(holeId, page, count);
    }

    /**
     * @api {get} /api/hole/posted 获取个人已发布的hole
     * @apiVersion 0.0.1
     * @apiName GetHolePosted
     * @apiGroup Hole
     * @apiDescription 获取个人已发布的hole
     * @apiHeader {String} STOKEN HOLE系统的用户Token，通过/api/hole/stoken/generate获得
     * @apiHeaderExample {json} Header-Example:
     * {
     * "STOKEN": "33d6e504d97cf2bfa43a26c63c1e9f25"
     * }
     * @apiParam {Integer} [page=1] 页码
     * @apiParam {Integer} [count=10] 每页数目
     * @apiParamExample {json} Request-Example:
     * {
     * "page": 3,
     * "count": 10
     * }
     * @apiSuccessExample {json} Success-Response:
     * {
     * "code": 0,
     * "message": "success",
     * "data": [
     * {
     * "hole": {
     * "holeId": 1,
     * "rootId": 0,
     * "parentId": 0,
     * "holeUserId": 3,
     * "content": "test!",
     * "date": "2020-12-29 16:23:07",
     * "like": 0,
     * "hate": 1,
     * "status": 0,
     * "type": 0
     * },
     * "holeUser": {
     * "holeUserId": 3,
     * "holeUserName": "南瓜",
     * "status": 0
     * }
     * },
     * {
     * "hole": {
     * "holeId": 5,
     * "rootId": 1,
     * "parentId": 1,
     * "holeUserId": 3,
     * "content": "回复222",
     * "date": "2020-12-29 17:42:20",
     * "like": 0,
     * "hate": 0,
     * "status": 0,
     * "type": 0
     * },
     * "holeUser": {
     * "holeUserId": 3,
     * "holeUserName": "南瓜",
     * "status": 0
     * }
     * }
     * ]
     * }
     */
    @GetMapping("/posted")
    Result getPostedHole(@RequestParam("_huid_") Long holeUserId,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer count) {
        return holeService.getPostedHole(holeUserId, page, count);
    }


}
