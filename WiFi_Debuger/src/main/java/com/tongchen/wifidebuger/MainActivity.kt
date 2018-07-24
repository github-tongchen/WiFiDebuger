package com.tongchen.wifidebuger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //  0为初始值，-1为USB调试,1为WiFi调试
    var mDebugType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDebugState()
        btn_start.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_start -> {
                if (mDebugType == -1) {
                    switch2WiFiDebuger()
                } else {
                    switch2USBDebuger()
                }
            }
        }
    }

    private fun initDebugState() {
        val successMsg = KotlinShellUtils.execCommand("getprop service.adb.tcp.port", true).successMsg
        //  空是重启手机后读取到的值，-1是自己切换后，退出重进读取到的值
        if (TextUtils.equals("", successMsg) || TextUtils.equals("-1", successMsg)) {
            mDebugType = -1
        } else {
            mDebugType = 0
        }
        refreshDebugState(mDebugType)
    }

    private fun refreshDebugState(debugState: Int) {
        if (debugState == -1) {
            tv_result.text = getString(R.string.usb_mode)
            btn_start.text = getString(R.string.switch_2_wifi_mode)
        } else {
            tv_result.text = getString(R.string.wifi_mode)
            btn_start.text = getString(R.string.switch_2_usb_mode)
        }
    }

    private fun checkRootPermission(): Boolean {
        val hasRoot = KotlinShellUtils.checkRootPermission()
        if (!hasRoot) {
            tv_result.text = getString(R.string.no_root_permission)
            return false
        }
        return true
    }

    private fun switch2WiFiDebuger() {
        if (!checkRootPermission()) {
            return
        }
        val cmdList: MutableList<String> = mutableListOf()
        cmdList.add("setprop service.adb.tcp.port 7777")
        cmdList.add("stop adbd")
        cmdList.add("start adbd")
        val cmdResult = KotlinShellUtils.execCommand(cmdList, true)
        if (cmdResult.result == 0) {
            mDebugType = 1
        } else {
            mDebugType = -1
        }
        refreshDebugState(mDebugType)
    }

    private fun switch2USBDebuger() {

        if (!checkRootPermission()) {
            return
        }
        val cmdList: MutableList<String> = mutableListOf()
        cmdList.add("setprop service.adb.tcp.port -1")
        cmdList.add("stop adbd")
        cmdList.add("start adbd")
        val cmdResult = KotlinShellUtils.execCommand(cmdList, true)
        if (cmdResult.result == 0) {
            mDebugType = -1
        } else {
            mDebugType = 1
        }
        refreshDebugState(mDebugType)
    }

}
