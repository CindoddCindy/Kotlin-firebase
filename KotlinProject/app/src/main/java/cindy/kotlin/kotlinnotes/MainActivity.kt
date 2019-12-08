package cindy.kotlin.kotlinnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val RC_SIGN_IN: Int =1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var  mGoogleSignInOption: GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureGoogleSgnIn()
        setUpUI()

        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun setUpUI(){
        btn_sign_in_google.setOnClickListener {
            signIn()
        }

    }

    private fun signIn(){
        val signInIntent : Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    private fun configureGoogleSgnIn(){
        mGoogleSignInOption= GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,mGoogleSignInOption)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                firebaseAuthwithGoogle(account)
            }
        }catch (e: ApiException){
            Toast.makeText(applicationContext, "Sign In failed",Toast.LENGTH_LONG).show()
        }

        }

    private fun firebaseAuthwithGoogle(account: GoogleSignInAccount) {

        val credential  = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                val intent = Intent(this, GetData::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(applicationContext,"Sign in failed", Toast.LENGTH_LONG).show()
            }
        }


    }


}
