package com.example.tcpchattingexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tcpchattingexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)



		binding.btnEnter.setOnClickListener {
			val intent = Intent(this, ChattingRoom::class.java)

			intent.putExtra("ip", "${binding.txtIP.text.toString()}")
			intent.putExtra("port", "${binding.txtPort.text.toString()}")
			intent.putExtra("nick", "${binding.txtNick.text.toString()}")

			startActivity(intent)
		}
	}
}