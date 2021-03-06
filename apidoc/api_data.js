define({
    "api": [
        {
            "type": "post",
            "url": "/api/auth/refresh",
            "title": "刷新token",
            "version": "0.0.1",
            "name": "TokenRefresh",
            "group": "Auth",
            "description": "<p>刷新token</p>",
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": false,
                            "field": "refresh_token",
                            "description": "<p>refresh_token</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": true,
                            "field": "k",
                            "description": "<p>唯一应用标识</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"refresh_token\": \"13846B0F4DFD9469C7EFCE9B0F8CA9E3\"\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "fields": {
                    "Success 200": [
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "token",
                            "description": "<p>Token</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "refresh_token",
                            "description": "<p>refresh_token</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\",\n\"data\": {\n\"refresh_token\": \"8E4B37904553B4538DAC7E94163FB091-581869\",\n\"token\": \"00D3CBBCA8ECAC8E355B3AB5356C9E53-576520\"\n}\n}",
                        "type": "json"
                    }
                ]
            },
            "error": {
                "fields": {
                    "Error 4xx": [
                        {
                            "group": "Error 4xx",
                            "optional": false,
                            "field": "40001",
                            "description": "<p>登录状态已失效，请重新登录</p>"
                        }
                    ]
                }
            },
            "filename": "./starvel-auth/src/main/java/com/starvel/auth/controller/AuthController.java",
            "groupTitle": "Auth"
        },
        {
            "type": "post",
            "url": "/api/auth/login",
            "title": "登录",
            "version": "0.0.1",
            "name": "UserLogin",
            "group": "Auth",
            "description": "<p>用户登录</p>",
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": false,
                            "field": "u",
                            "description": "<p>用户名或邮箱</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": false,
                            "field": "p",
                            "description": "<p>密码</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": true,
                            "field": "k",
                            "description": "<p>唯一应用标识</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"u\": \"sky\",\n\"p\": \"password123\"\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "fields": {
                    "Success 200": [
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "token",
                            "description": "<p>Token</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\",\n\"data\": {\n\"refresh_token\": \"8E4B37950453BF4538DAC7E94163FB091-581869\",\n\"token\": \"00D3CBBCA8ECA5C8E35B03AB5356C9E53-576520\"\n}\n}",
                        "type": "json"
                    }
                ]
            },
            "error": {
                "fields": {
                    "Error 4xx": [
                        {
                            "group": "Error 4xx",
                            "optional": false,
                            "field": "40011",
                            "description": "<p>你的帐号或密码输错了哦</p>"
                        }
                    ]
                }
            },
            "filename": "./starvel-auth/src/main/java/com/starvel/auth/controller/AuthController.java",
            "groupTitle": "Auth"
        },
        {
            "type": "post",
            "url": "/api/auth/register",
            "title": "注册",
            "version": "0.0.1",
            "name": "UserRegister",
            "group": "Auth",
            "description": "<p>用户注册</p>",
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": false,
                            "field": "userName",
                            "description": "<p>用户名</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": false,
                            "field": "password",
                            "description": "<p>密码</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": false,
                            "field": "email",
                            "description": "<p>邮箱,后缀为<code>@mail.sdu.edu.cn</code></p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"userName\": \"这是一个用户名\",\n\"password\": \"password123\",\n\"email\": \"20103812442@mail.sdu.edu.cn\"\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "fields": {
                    "Success 200": [
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "token",
                            "description": "<p>Token</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\"\n}",
                        "type": "json"
                    }
                ]
            },
            "error": {
                "fields": {
                    "Error 4xx": [
                        {
                            "group": "Error 4xx",
                            "optional": false,
                            "field": "40012",
                            "description": "<p>用户名长度应为2~16位</p>"
                        },
                        {
                            "group": "Error 4xx",
                            "optional": false,
                            "field": "40013",
                            "description": "<p>用户名不可包含除-和_以外的特殊字符</p>"
                        },
                        {
                            "group": "Error 4xx",
                            "optional": false,
                            "field": "40014",
                            "description": "<p>密码长度应为6~16位</p>"
                        }
                    ]
                }
            },
            "filename": "./starvel-auth/src/main/java/com/starvel/auth/controller/AuthController.java",
            "groupTitle": "Auth"
        },
        {
            "success": {
                "fields": {
                    "Success 200": [
                        {
                            "group": "Success 200",
                            "optional": false,
                            "field": "varname1",
                            "description": "<p>No type.</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "varname2",
                            "description": "<p>With type.</p>"
                        }
                    ]
                }
            },
            "type": "",
            "url": "",
            "version": "0.0.0",
            "filename": "./apidoc/main.js",
            "group": "F:\\others\\Desktop\\project\\remote\\hole\\apidoc\\main.js",
            "groupTitle": "F:\\others\\Desktop\\project\\remote\\hole\\apidoc\\main.js",
            "name": ""
        },
        {
            "type": "post",
            "url": "/api/hole/stoken/generate",
            "title": "STOKEN生成",
            "version": "0.0.1",
            "name": "GenerateSToken",
            "group": "Hole",
            "description": "<p>STOKEN生成</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "TOKEN",
                            "description": "<p>系统的用户TOKEN，通过系统登录获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"TOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\",\n\"data\": {\n\"stoken\": \"9C7B5EA64036265886B3C2A236205F98D-352209\"\n}\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-hole/src/main/java/com/starvel/hole/controller/HoleController.java",
            "groupTitle": "Hole"
        },
        {
            "type": "get",
            "url": "/api/hole/info",
            "title": "获取树洞信息",
            "version": "0.0.1",
            "name": "GetHoleInfo",
            "group": "Hole",
            "description": "<p>获取树洞信息</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "STOKEN",
                            "description": "<p>HOLE系统的用户Token，通过/api/hole/stoken/generate获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"STOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "Long",
                            "optional": false,
                            "field": "holeId",
                            "description": "<p>树洞ID</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"holeId\":27730001\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "fields": {
                    "Success 200": [
                        {
                            "group": "Success 200",
                            "type": "Long",
                            "optional": false,
                            "field": "holeId",
                            "description": "<p>树洞唯一递增ID</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "Long",
                            "optional": false,
                            "field": "rootId",
                            "description": "<p>根树洞ID</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "Long",
                            "optional": false,
                            "field": "parentId",
                            "description": "<p>父树洞ID</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "Long",
                            "optional": false,
                            "field": "holeUserId",
                            "description": "<p>洞主ID</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "content",
                            "description": "<p>树洞内容</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "Long",
                            "optional": false,
                            "field": "date",
                            "description": "<p>发布时间的时间戳</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "Integer",
                            "optional": false,
                            "field": "like",
                            "description": "<p>点赞数</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "Integer",
                            "optional": false,
                            "field": "status",
                            "description": "<p>状态，0为默认，这个属性留着以后拓展</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "Integer",
                            "optional": false,
                            "field": "type",
                            "description": "<p>类型，0为默认，1为顶置..这个属性留着以后拓展</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "Object",
                            "optional": false,
                            "field": "holePoster",
                            "description": "<p>树洞用户</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "holeUserName",
                            "description": "<p>树洞用户随机名称</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\",\n\"data\": {\n\"hole\": {\n\"holeId\": 1,\n\"rootId\": 0,\n\"parentId\": 0,\n\"holeUserId\": 3,\n\"content\": \"test!\",\n\"date\": \"2020-12-29 16:23:07\",\n\"like\": 0,\n\"hate\": 0,\n\"status\": 0,\n\"type\": 0\n},\n\"holePoster\": {\n\"holeUserId\": 3,\n\"holeUserName\": \"南瓜\",\n\"status\": 0\n}\n}\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-hole/src/main/java/com/starvel/hole/controller/HoleController.java",
            "groupTitle": "Hole"
        },
        {
            "type": "get",
            "url": "/api/hole/list",
            "title": "获取hole列表",
            "version": "0.0.1",
            "name": "GetHoleList",
            "group": "Hole",
            "description": "<p>根据rootId属性获取hole列表</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "STOKEN",
                            "description": "<p>HOLE系统的用户Token，通过/api/hole/stoken/generate获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"STOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "Long",
                            "optional": false,
                            "field": "holeId",
                            "description": "<p>树洞ID，若为0，则返回最新hole</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "Integer",
                            "optional": true,
                            "field": "page",
                            "defaultValue": "1",
                            "description": "<p>页码</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "Integer",
                            "optional": true,
                            "field": "count",
                            "defaultValue": "10",
                            "description": "<p>每页数目</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"holeId\":27730001,\n\"page\": 3,\n\"count\": 10\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n    \"code\": 0,\n    \"message\": \"success\",\n    \"data\": [\n        {\n            \"hole\": {\n                \"holeId\": 1,\n                \"rootId\": 0,\n                \"parentId\": 0,\n                \"holeUserId\": 3,\n                \"content\": \"test!\",\n                \"date\": \"2020-12-29 16:23:07\",\n                \"like\": 0,\n                \"hate\": 1,\n                \"status\": 0,\n                \"type\": 0\n            },\n            \"holeUser\": {\n                \"holeUserId\": 3,\n                \"holeUserName\": \"南瓜\",\n                \"status\": 0\n            }\n        },\n        {\n            \"hole\": {\n                \"holeId\": 5,\n                \"rootId\": 1,\n                \"parentId\": 1,\n                \"holeUserId\": 3,\n                \"content\": \"回复222\",\n                \"date\": \"2020-12-29 17:42:20\",\n                \"like\": 0,\n                \"hate\": 0,\n                \"status\": 0,\n                \"type\": 0\n            },\n            \"holeUser\": {\n                \"holeUserId\": 3,\n                \"holeUserName\": \"南瓜\",\n                \"status\": 0\n            }\n        }\n    ]\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-hole/src/main/java/com/starvel/hole/controller/HoleController.java",
            "groupTitle": "Hole"
        },
        {
            "type": "get",
            "url": "/api/hole/posted",
            "title": "获取个人已发布的hole",
            "version": "0.0.1",
            "name": "GetHolePosted",
            "group": "Hole",
            "description": "<p>获取个人已发布的hole</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "STOKEN",
                            "description": "<p>HOLE系统的用户Token，通过/api/hole/stoken/generate获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"STOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "Integer",
                            "optional": true,
                            "field": "page",
                            "defaultValue": "1",
                            "description": "<p>页码</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "Integer",
                            "optional": true,
                            "field": "count",
                            "defaultValue": "10",
                            "description": "<p>每页数目</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"page\": 3,\n\"count\": 10\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n    \"code\": 0,\n    \"message\": \"success\",\n    \"data\": [\n        {\n            \"hole\": {\n                \"holeId\": 1,\n                \"rootId\": 0,\n                \"parentId\": 0,\n                \"holeUserId\": 3,\n                \"content\": \"test!\",\n                \"date\": \"2020-12-29 16:23:07\",\n                \"like\": 0,\n                \"hate\": 1,\n                \"status\": 0,\n                \"type\": 0\n            },\n            \"holeUser\": {\n                \"holeUserId\": 3,\n                \"holeUserName\": \"南瓜\",\n                \"status\": 0\n            }\n        },\n        {\n            \"hole\": {\n                \"holeId\": 5,\n                \"rootId\": 1,\n                \"parentId\": 1,\n                \"holeUserId\": 3,\n                \"content\": \"回复222\",\n                \"date\": \"2020-12-29 17:42:20\",\n                \"like\": 0,\n                \"hate\": 0,\n                \"status\": 0,\n                \"type\": 0\n            },\n            \"holeUser\": {\n                \"holeUserId\": 3,\n                \"holeUserName\": \"南瓜\",\n                \"status\": 0\n            }\n        }\n    ]\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-hole/src/main/java/com/starvel/hole/controller/HoleController.java",
            "groupTitle": "Hole"
        },
        {
            "type": "get",
            "url": "/api/hole/reply",
            "title": "获取hole回复",
            "version": "0.0.1",
            "name": "GetHoleReply",
            "group": "Hole",
            "description": "<p>根据parentId属性获取hole回复</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "STOKEN",
                            "description": "<p>HOLE系统的用户Token，通过/api/hole/stoken/generate获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"STOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "Long",
                            "optional": false,
                            "field": "holeId",
                            "description": "<p>树洞ID</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "Integer",
                            "optional": true,
                            "field": "page",
                            "defaultValue": "1",
                            "description": "<p>页码</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "Integer",
                            "optional": true,
                            "field": "count",
                            "defaultValue": "10",
                            "description": "<p>每页数目</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"holeId\":27730001,\n\"page\": 3,\n\"count\": 10\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n    \"code\": 0,\n    \"message\": \"success\",\n    \"data\": [\n        {\n            \"hole\": {\n                \"holeId\": 1,\n                \"rootId\": 0,\n                \"parentId\": 0,\n                \"holeUserId\": 3,\n                \"content\": \"test!\",\n                \"date\": \"2020-12-29 16:23:07\",\n                \"like\": 0,\n                \"hate\": 1,\n                \"status\": 0,\n                \"type\": 0\n            },\n            \"holeUser\": {\n                \"holeUserId\": 3,\n                \"holeUserName\": \"南瓜\",\n                \"status\": 0\n            }\n        },\n        {\n            \"hole\": {\n                \"holeId\": 5,\n                \"rootId\": 1,\n                \"parentId\": 1,\n                \"holeUserId\": 3,\n                \"content\": \"回复222\",\n                \"date\": \"2020-12-29 17:42:20\",\n                \"like\": 0,\n                \"hate\": 0,\n                \"status\": 0,\n                \"type\": 0\n            },\n            \"holeUser\": {\n                \"holeUserId\": 3,\n                \"holeUserName\": \"南瓜\",\n                \"status\": 0\n            }\n        }\n    ]\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-hole/src/main/java/com/starvel/hole/controller/HoleController.java",
            "groupTitle": "Hole"
        },
        {
            "type": "post",
            "url": "/api/hole/hate",
            "title": "点踩或取消点踩",
            "version": "0.0.1",
            "name": "HateHole",
            "group": "Hole",
            "description": "<p>点踩或取消点踩，若没有点过踩，则点踩；若已点踩，则为取消点踩</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "STOKEN",
                            "description": "<p>HOLE系统的用户Token，通过/api/hole/stoken/generate获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"STOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "Long",
                            "optional": false,
                            "field": "holeId",
                            "description": "<p>树洞ID</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"holeId\":27730001\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\"\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-hole/src/main/java/com/starvel/hole/controller/HoleController.java",
            "groupTitle": "Hole"
        },
        {
            "type": "get",
            "url": "/api/hole/has/hate",
            "title": "是否点过踩",
            "version": "0.0.1",
            "name": "IfHateHole",
            "group": "Hole",
            "description": "<p>是否点过踩, 是返回true，否返回false</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "STOKEN",
                            "description": "<p>HOLE系统的用户Token，通过/api/hole/stoken/generate获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"STOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "Long",
                            "optional": false,
                            "field": "holeId",
                            "description": "<p>树洞ID</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"holeId\":27730001\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\",\n\"data\": true\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-hole/src/main/java/com/starvel/hole/controller/HoleController.java",
            "groupTitle": "Hole"
        },
        {
            "type": "get",
            "url": "/api/hole/has/like",
            "title": "是否点过赞",
            "version": "0.0.1",
            "name": "IfLikeHole",
            "group": "Hole",
            "description": "<p>是否点过赞, 是返回true，否返回false</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "STOKEN",
                            "description": "<p>HOLE系统的用户Token，通过/api/hole/stoken/generate获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"STOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "Long",
                            "optional": false,
                            "field": "holeId",
                            "description": "<p>树洞ID</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"holeId\":27730001\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\",\n\"data\": true\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-hole/src/main/java/com/starvel/hole/controller/HoleController.java",
            "groupTitle": "Hole"
        },
        {
            "type": "post",
            "url": "/api/hole/like",
            "title": "点赞或取消点赞",
            "version": "0.0.1",
            "name": "LikeHole",
            "group": "Hole",
            "description": "<p>点赞或取消点赞，若没有点过赞，则点赞；若已点赞，则为取消点赞</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "STOKEN",
                            "description": "<p>HOLE系统的用户Token，通过/api/hole/stoken/generate获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"STOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "Long",
                            "optional": false,
                            "field": "holeId",
                            "description": "<p>树洞ID</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"holeId\":27730001\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\"\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-hole/src/main/java/com/starvel/hole/controller/HoleController.java",
            "groupTitle": "Hole"
        },
        {
            "type": "post",
            "url": "/api/hole/post",
            "title": "发布树洞",
            "version": "0.0.1",
            "name": "PostHole",
            "group": "Hole",
            "description": "<p>发布树洞</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "STOKEN",
                            "description": "<p>HOLE系统的用户Token，通过/api/hole/stoken/generate获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"STOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "Long",
                            "optional": false,
                            "field": "parentId",
                            "description": "<p>父树洞，若是新开树洞则为0</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": false,
                            "field": "content",
                            "description": "<p>内容</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": true,
                            "field": "type",
                            "defaultValue": "0",
                            "description": "<p>类型</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"parentId\":27623183,\n\"content\":\"这是树洞的内容\",\n\"type\":0\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\",\n\"data\": {\n\"hole\": {\n\"holeId\": 1,\n\"rootId\": 0,\n\"parentId\": 0,\n\"holeUserId\": 3,\n\"content\": \"test!\",\n\"date\": \"2020-12-29 16:23:07\",\n\"like\": 0,\n\"hate\": 0,\n\"status\": 0,\n\"type\": 0\n},\n\"holePoster\": {\n\"holeUserId\": 3,\n\"holeUserName\": \"南瓜\",\n\"status\": 0\n}\n}\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-hole/src/main/java/com/starvel/hole/controller/HoleController.java",
            "groupTitle": "Hole"
        },
        {
            "type": "post",
            "url": "/api/basic_user/email/bind",
            "title": "绑定邮箱",
            "version": "0.0.1",
            "name": "BindEmail",
            "group": "User",
            "description": "<p>绑定邮箱</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "TOKEN",
                            "description": "<p>系统的用户TOKEN，通过系统登录获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"TOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": false,
                            "field": "email",
                            "description": "<p>邮箱</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"email\":\"201900209891@email.sdu.edu.cn\"\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\"\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-forum/src/main/java/com/starvel/forum/controller/BasicUserController.java",
            "groupTitle": "User"
        },
        {
            "type": "post",
            "url": "/api/basic_user/stu_num/bind",
            "title": "绑定学号",
            "version": "0.0.1",
            "name": "BindStuNum",
            "group": "User",
            "description": "<p>绑定学号</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "TOKEN",
                            "description": "<p>系统的用户TOKEN，通过系统登录获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"TOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": false,
                            "field": "stuNum",
                            "description": "<p>学号</p>"
                        },
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": false,
                            "field": "password",
                            "description": "<p>统一身份认证密码</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"stuNum\":\"201900209891\",\n\"password\":\"hsajd\"\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\"\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-forum/src/main/java/com/starvel/forum/controller/BasicUserController.java",
            "groupTitle": "User"
        },
        {
            "type": "get",
            "url": "/api/basic_user/info",
            "title": "获取个人信息",
            "version": "0.0.1",
            "name": "GetUserInfo",
            "group": "User",
            "description": "<p>获取个人信息</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "TOKEN",
                            "description": "<p>系统的用户TOKEN，通过系统登录获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"TOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "fields": {
                    "Success 200": [
                        {
                            "group": "Success 200",
                            "type": "Integer",
                            "optional": false,
                            "field": "userId",
                            "description": "<p>用户唯一识别Id</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "studentNum",
                            "description": "<p>学号</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "userName",
                            "description": "<p>姓名</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "gender",
                            "description": "<p>性别</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "Integer",
                            "optional": false,
                            "field": "age",
                            "description": "<p>年龄</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "email",
                            "description": "<p>邮箱</p>"
                        },
                        {
                            "group": "Success 200",
                            "type": "String",
                            "optional": false,
                            "field": "introduction",
                            "description": "<p>个人简介</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\",\n\"data\": {\n\"userId\": 1,\n\"studentNum\": \"2066\",\n\"userName\": \"sky\",\n\"gender\": 1,\n\"age\": 17,\n\"email\": \"skyyemperor@qq.com\",\n\"introduction\": \"爱好学习，对世界充满好奇\"\n}\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-forum/src/main/java/com/starvel/forum/controller/BasicUserController.java",
            "groupTitle": "User"
        },
        {
            "type": "post",
            "url": "/api/basic_user/info/update",
            "title": "更新个人信息",
            "version": "0.0.1",
            "name": "UpdatePersonInfo",
            "group": "User",
            "description": "<p>更新个人信息</p>",
            "header": {
                "fields": {
                    "Header": [
                        {
                            "group": "Header",
                            "type": "String",
                            "optional": false,
                            "field": "TOKEN",
                            "description": "<p>系统的用户TOKEN，通过系统登录获得</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Header-Example:",
                        "content": "{\n\"TOKEN\": \"33d6e504d97cf2bfa43a26c63c1e9f25\"\n}",
                        "type": "json"
                    }
                ]
            },
            "parameter": {
                "fields": {
                    "Parameter": [
                        {
                            "group": "Parameter",
                            "type": "String",
                            "optional": false,
                            "field": "introduction",
                            "description": "<p>个人简介</p>"
                        }
                    ]
                },
                "examples": [
                    {
                        "title": "Request-Example:",
                        "content": "{\n\"introduction\":\"nuleel\"\n}",
                        "type": "json"
                    }
                ]
            },
            "success": {
                "examples": [
                    {
                        "title": "Success-Response:",
                        "content": "{\n\"code\": 0,\n\"message\": \"success\"\n}",
                        "type": "json"
                    }
                ]
            },
            "filename": "./starvel-forum/src/main/java/com/starvel/forum/controller/BasicUserController.java",
            "groupTitle": "User"
        }
    ]
});
