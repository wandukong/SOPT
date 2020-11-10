package org.wandukong.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var myObserver: MyObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myObserver = MyObserver(this, lifecycle)
        lifecycle.addObserver(myObserver)

        buttonPressed()
    }

    private fun buttonPressed(){
        download.setOnClickListener {
            if(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)){ // 현재 상태가 STARTED 상태 이후 인지 확인.
                Toast.makeText(applicationContext,"You can now download your music...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}