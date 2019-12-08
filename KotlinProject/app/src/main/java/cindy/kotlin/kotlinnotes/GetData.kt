package cindy.kotlin.kotlinnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GetData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_data)
        pindah()
    }

    private fun  pindah(){
        val intent = Intent(this, AddNote:: class.java)
        startActivity(intent)
        finish()
    }
}
