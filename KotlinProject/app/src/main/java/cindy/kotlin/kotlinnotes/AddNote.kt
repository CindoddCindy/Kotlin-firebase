package cindy.kotlin.kotlinnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNote : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        databaseReference = FirebaseDatabase.getInstance().getReference("Notes")
        btn_add_note.setOnClickListener {
            sendNote()
        }
    }

    private fun sendNote(){
         val title : String = et_satu.text.toString()
        val desc : String = et_dua.text.toString()

        val userId: String = databaseReference.push().key.toString()
        val filenote = NoteModel(title,desc)

        databaseReference.child(userId).setValue(filenote)
            .addOnCompleteListener {
                et_satu.setText("")
                et_dua.setText("")
                Toast.makeText(this,"success",Toast.LENGTH_LONG).show()
                val intent = Intent(this, GetData:: class.java)
                startActivity(intent)
                finish()
            }
    }
}
