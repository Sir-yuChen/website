let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'DicDataController',
    order: '1',
    link: '字典数据_dicdatacontroller',
    desc: '字典数据 DicDataController',
    list: []
})
api[0].list.push({
    alias: 'DictionaryController',
    order: '2',
    link: '字典_dictionarycontroller',
    desc: '字典 DictionaryController',
    list: []
})
api[0].list.push({
    alias: 'ExternalController',
    order: '3',
    link: '第三方接口信息',
    desc: '第三方接口信息',
    list: []
})
api[0].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8090/external/friendLinks',
    desc: '友链',
});
api[0].list.push({
    alias: 'FilmController',
    order: '4',
    link: '视频_filmcontroller',
    desc: '视频 FilmController',
    list: []
})
api[0].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8090/api/film/getFilm',
    desc: '视频详情[单查]',
});
api[0].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8090/api/film/videoChart',
    desc: '视频榜',
});
api[0].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8090/api/film/filmSearchBar',
    desc: '搜索框搜索',
});
api[0].list[3].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8090/api/film/frontPageFilm',
    desc: '首页视频展示',
});
api[0].list.push({
    alias: 'FilmImageController',
    order: '5',
    link: '图片_filmimagecontroller',
    desc: '图片 FilmImageController',
    list: []
})
api[0].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8090/api/filmImage/carousel',
    desc: '获取首页轮播图',
});
api[0].list.push({
    alias: 'FilmMenuController',
    order: '6',
    link: '菜单_filmmenucontroller',
    desc: '菜单 FilmMenuController',
    list: []
})
api[0].list[5].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8090/api/menu/getTopMenu',
    desc: '菜单加载',
});
api[0].list.push({
    alias: 'FilmScoreController',
    order: '7',
    link: '视频评分_filmscorecontroller',
    desc: '视频评分 FilmScoreController',
    list: []
})
api[0].list.push({
    alias: 'FilmTypeController',
    order: '8',
    link: '视频类型_filmtypecontroller',
    desc: '视频类型 FilmTypeController',
    list: []
})
api[0].list.push({
    alias: 'HelloController',
    order: '9',
    link: 'hellocontroller',
    desc: 'HelloController',
    list: []
})
api[0].list[8].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8090/',
    desc: 'website  测试接口',
});
api[0].list.push({
    alias: 'PersonInfoController',
    order: '10',
    link: '人物_personinfocontroller',
    desc: '人物 PersonInfoController',
    list: []
})
api[0].list.push({
    alias: 'PlayRecordController',
    order: '11',
    link: '播放记录_playrecordcontroller',
    desc: '播放记录 PlayRecordController',
    list: []
})
api[0].list[10].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8090/api/play/record',
    desc: '获取视频播放记录',
});
api[0].list[10].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8090/api/play/saveRecord',
    desc: '保存视频播放记录',
});
api[0].list[10].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8090/api/play/clearRecord',
    desc: '清除播放记录',
});
api[0].list.push({
    alias: 'ProxyIpController',
    order: '12',
    link: 'ip代理池',
    desc: 'IP代理池',
    list: []
})
api[0].list[11].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8090/proxyIp/getIp',
    desc: '批量爬取IP',
});
api[0].list.push({
    alias: 'RateLimiterController',
    order: '13',
    link: '令牌限流测试_ratelimitercontroller',
    desc: '令牌限流测试 RateLimiterController',
    list: []
})
api[0].list[12].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8090/ratelimiter/open',
    desc: '开启限流【10QPS,策略一】',
});
api[0].list[12].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8090/ratelimiter/open2',
    desc: '开启限流【10QPS,策略二】',
});
api[0].list[12].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8090/ratelimiter/close',
    desc: '未开启限流',
});
api[0].list.push({
    alias: 'UserController',
    order: '14',
    link: '用户相关_usercontroller',
    desc: '用户相关 UserController',
    list: []
})
api[0].list[13].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8090/api/user/login',
    desc: '用户登录',
});
api[0].list[13].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8090/api/user/logout',
    desc: '用户退登',
});
api[0].list[13].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8090/api/user/queryCurrentUser',
    desc: '获取用户信息',
});
api[0].list.push({
    alias: 'VerifyController',
    order: '15',
    link: '校验_verifycontroller',
    desc: '校验 VerifyController',
    list: []
})
api[0].list[14].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8090/api/verify/captcha',
    desc: '随机验证码',
});
api[0].list[14].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8090/api/verify/checkCaptcha',
    desc: '验证码校验',
});
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value.toLocaleLowerCase();

        let searchGroup = [];
        for (let i = 0; i < api.length; i++) {

            let apiGroup = api[i];

            let searchArr = [];
            for (let i = 0; i < apiGroup.list.length; i++) {
                let apiData = apiGroup.list[i];
                const desc = apiData.desc;
                if (desc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                    searchArr.push({
                        order: apiData.order,
                        desc: apiData.desc,
                        link: apiData.link,
                        list: apiData.list
                    });
                } else {
                    let methodList = apiData.list || [];
                    let methodListTemp = [];
                    for (let j = 0; j < methodList.length; j++) {
                        const methodData = methodList[j];
                        const methodDesc = methodData.desc;
                        if (methodDesc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                            methodListTemp.push(methodData);
                            break;
                        }
                    }
                    if (methodListTemp.length > 0) {
                        const data = {
                            order: apiData.order,
                            desc: apiData.desc,
                            link: apiData.link,
                            list: methodListTemp
                        };
                        searchArr.push(data);
                    }
                }
            }
            if (apiGroup.name.toLocaleLowerCase().indexOf(searchValue) > -1) {
                searchGroup.push({
                    name: apiGroup.name,
                    order: apiGroup.order,
                    list: searchArr
                });
                continue;
            }
            if (searchArr.length === 0) {
                continue;
            }
            searchGroup.push({
                name: apiGroup.name,
                order: apiGroup.order,
                list: searchArr
            });
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchGroup,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiGroups, liClass, display) {
    let html = "";
    let doc;
    if (apiGroups.length > 0) {
         if (apiDocListSize == 1) {
            let apiData = apiGroups[0].list;
            for (let j = 0; j < apiData.length; j++) {
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                html += '<ul class="sectlevel2" style="'+display+'">';
                doc = apiData[j].list;
                for (let m = 0; m < doc.length; m++) {
                    let spanString;
                    if (doc[m].deprecated == 'true') {
                        spanString='<span class="line-through">';
                    } else {
                        spanString='<span>';
                    }
                    html += '<li><a href="#_1_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
                }
                html += '</ul>';
                html += '</li>';
            }
        } else {
            for (let i = 0; i < apiGroups.length; i++) {
                let apiGroup = apiGroups[i];
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_' + apiGroup.name + '">' + apiGroup.order + '.&nbsp;' + apiGroup.name + '</a>';
                html += '<ul class="sectlevel1">';

                let apiData = apiGroup.list;
                for (let j = 0; j < apiData.length; j++) {
                    html += '<li class="'+liClass+'">';
                    html += '<a class="dd" href="#_'+apiGroup.order+'_'+ apiData[j].order + '_'+ apiData[j].link + '">' +apiGroup.order+'.'+ apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                    html += '<ul class="sectlevel2" style="'+display+'">';
                    doc = apiData[j].list;
                    for (let m = 0; m < doc.length; m++) {
                       let spanString;
                       if (doc[m].deprecated == 'true') {
                           spanString='<span class="line-through">';
                       } else {
                           spanString='<span>';
                       }
                       html += '<li><a href="#_'+apiGroup.order+'_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">'+apiGroup.order+'.' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
                   }
                    html += '</ul>';
                    html += '</li>';
                }

                html += '</ul>';
                html += '</li>';
            }
        }
    }
    return html;
}