package com.tongchen.wifidebuger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_start -> startWiFiDebuger()
        }
    }

    private fun startWiFiDebuger() {

        val hasRoot = ShellUtils.checkRootPermission()
        if (!hasRoot) {
            tv_result.text = "没有获取Root权限，请到设置中给应用Root权限"
            return
        }
        val cmdList: MutableList<String> = mutableListOf()
        cmdList.add("setprop service.adb.tcp.port 7777")
        cmdList.add("stop adbd")
        cmdList.add("start adbd")
        if (ShellUtils.execCommand(cmdList, true).result != 0) {
            tv_result.text = "WIFi调试开启失败，请重试"
        }

    }

}
