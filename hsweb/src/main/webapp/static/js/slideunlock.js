/**
 * jquery plugin -- jquery.slideunlock.js
 * Description: a slideunlock plugin based on jQuery
 * Version: 1.1
 * Author: stella ning
 * created: May 05, 2017
 */
;(function($,window,document,undefined){
    function SliderUnlock(elm,options,success){
        var me = this;
            $elm = me.checkElm(elm)? $(elm) : $;
        success = me.checkFn(success) ? success : function(){};

        var opts = {
            successLabelTip: '验证成功',//返回信息
            duration: 200,//持续的时间
            swipestart: false,//是否开始滑动
            min: 0,//最小值
            max: $elm.width(),//最大值
            index: 0,
            IsOk: false,
            lableIndex: 0
        };

        //继承
        opts = $.extend(opts,options||{});

        //$elm
        me.elm = $elm;
        //opts
        me.opts = opts;
        //是否开始滑动
        me.swipestart = opts.swipestart;
        //最小值
        me.min = opts.min;
        //最大值
        me.max = opts.max;
        //当前滑动条所处的位置
        me.index = opts.index;
        //是否滑动成功
        me.isOk = opts.isOk;
        //滑块宽度
        me.labelWidth = me.elm.find('#label').width();
        //滑块背景
        me.sliderBg = me.elm.find('#slider_bg');
        //鼠标在滑动按钮的位置
        me.lableIndex = opts.lableIndex;
        //success
        me.success = success;
    }

    //操作方法
    SliderUnlock.prototype.init = function () {
        var me = this;
        me.updateView();
        me.elm.find("#label").on("mousedown", function (event) {
            var e = event || window.event;
            me.lableIndex = e.clientX - this.offsetLeft;
            me.handerIn();
        }).on("mousemove", function (event) {
            me.handerMove(event);
        }).on("mouseup", function (event) {
            me.handerOut();
        }).on("mouseout", function (event) {
            me.handerOut();
        }).on("touchstart", function (event) {
            var e = event || window.event;
            me.lableIndex = e.originalEvent.touches[0].pageX - this.offsetLeft;
            me.handerIn();
        }).on("touchmove", function (event) {
            me.handerMove(event, "mobile");
        }).on("touchend", function (event) {
            me.handerOut();
        });
    };
    /**
     * 鼠标/手指接触滑动按钮
     */
    SliderUnlock.prototype.handerIn = function () {
        var userName = $('#userName').val();
        var pass = $('#password').val();
        if(userName=='' || pass==''){
            layer.msg("请输入登录账号和密码");
            return;
        }
        var me = this;
        me.swipestart = true;
        me.min = 0;
        me.max = me.elm.width();
    };

    /**
     * 鼠标/手指移出
     */
    SliderUnlock.prototype.handerOut = function () {
        var me = this;
        //停止
        me.swipestart = false;
        //me.move();
        if (me.index < me.max) {
            me.reset();
        }
    };

    /**
     * 鼠标/手指移动
     * @param event
     * @param type
     */
    SliderUnlock.prototype.handerMove = function (event, type) {
        var me = this;
        if (me.swipestart) {
            event.preventDefault();
            event = event || window.event;
            if (type == "mobile") {
                me.index = event.originalEvent.touches[0].pageX - me.lableIndex;
            } else {
                me.index = event.clientX - me.lableIndex;
            }
            me.move();
        }
    };

    /**
     * 鼠标/手指移动过程
     */
    SliderUnlock.prototype.move = function () {
        var me = this;
        if ((me.index + me.labelWidth) >= me.max) {
            me.index = me.max - me.labelWidth -2;
            //停止
            me.swipestart = false;
            //解锁
            me.isOk = true;
        }
        if (me.index < 0) {
            me.index = me.min;
            //未解锁
            me.isOk = false;
        }
        if (me.index+me.labelWidth+2 == me.max && me.max > 0 && me.isOk) {
            //解锁默认操作
            $('#label').unbind().next('#labelTip').
            text(me.opts.successLabelTip).css({'color': '#fff'});
            me.success();
            //解锁成功，通知云端
        }
        me.updateView();
    };

    /**
     * 更新视图
     */
    SliderUnlock.prototype.updateView = function () {
        var me = this;
        me.sliderBg.css('width', me.index);
        me.elm.find("#label").css("left", me.index + "px")
    };

    /**
     * 重置slide的起点
     */
    SliderUnlock.prototype.reset = function () {
        var me = this;
        me.index = 0;
        me.sliderBg.animate({'width':0},me.opts.duration);
        me.elm.find("#label").animate({left: me.index}, me.opts.duration)
            .next("#lableTip").animate({opacity: 1}, me.opts.duration);
    };
    
    SliderUnlock.prototype.restart = function () {
        var me = this;
        me.index = 0;
        me.sliderBg.animate({'width':0},me.opts.duration);
        me.elm.find("#label").animate({left: me.index}, me.opts.duration)
            .next("#lableTip").animate({opacity: 1}, me.opts.duration);
        $('#labelTip').text('拖动滑块验证').css({'color': '#787878'});
        me.init();
    }


    /**
      * 检测元素是否存在
      * @param elm
      * @returns {boolean}
    */
    SliderUnlock.prototype.checkElm = function(elm){
        if($(elm).length > 0){
            console.log('挂载成功');
            return true;
        }else{
            throw "挂载元素不存在"
        }
    }

    /**
     * 检测传入参数是否是function
     * @param fn
     * @returns {boolean}
    */
    SliderUnlock.prototype.checkFn = function(fn){
        if(typeof fn === 'function'){
            console.log('function');
            return true;
        }else{
            throw 'the param is not a function.'
        }
    };
    window['SliderUnlock'] = SliderUnlock;
    
})(jQuery,window,document);
