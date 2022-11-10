package com.example.tcpchattingexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import com.example.tcpchattingexam.databinding.ActivityChattingRoomBinding
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class ChattingRoom : AppCompatActivity() {
	var rcTxt: TextView? = null
	var scv: ScrollView? = null
	var dos: DataOutputStream? = null
	var pname = ""
	var ip = ""
	var port = 7777
	var nick = ""


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_chatting_room)

		ip = intent.getStringExtra("ip")!!.toString()
		nick = intent.getStringExtra("nick")!!.toString()

		if(!intent.getStringExtra("port").equals("")){
			port = intent.getStringExtra("port")!!.toInt()
		}

		if(!intent.getStringExtra("port").equals("")){
			port = intent.getStringExtra("port")!!.toInt()
		}

		rcTxt = findViewById(R.id.rcTxt)
		scv = findViewById(R.id.scv)

		ReceiverTh().start()

		findViewById<EditText>(R.id.sendTxt).setOnEditorActionListener {
				me, actionId, event ->

			Log.d("sendTxt 여",  "${me.text}")
			var msg = "${me.text}"

			if(dos != null) {
				object : Thread() {
					override fun run() {
						dos!!.writeUTF(pname + msg)
					}
				}.start()
			}


			me.text = ""
			false

		}

	}

	inner class ReceiverTh:Thread(){		// inner class 사용이유는 outer class 멤버변수를 사용하기 위하여 사용함
		override fun run() {		// socket을 onCreate메소드에서 만들면 에러가 나므로 run메소드를 사용하여 호출함
			var socket = Socket("${ip.toString()}", port.toInt())

			pname = "[${nick}]"

			dos = DataOutputStream(socket.getOutputStream())
			var dis = DataInputStream(socket.getInputStream())

			try{

				while (dis!=null){
					val msg = dis.readUTF()
					runOnUiThread{
						rcTxt!!.append(msg + "\n")
						scv!!.fullScroll(ScrollView.FOCUS_DOWN)
					}
				}

			}catch (e: Exception){

			}finally {
				dis.close()
				dos!!.close()
				socket.close()
				dos = null
			}


		}
	}

}