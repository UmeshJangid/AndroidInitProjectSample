package com.app.androidinitialproject.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import com.app.androidinitialproject.AppManager
import com.app.androidinitialproject.R
import com.app.androidinitialproject.databinding.FragmentLoginBinding
import com.app.androidinitialproject.domain.annotations.InputErrorType
import com.app.androidinitialproject.ui.dashboard.DashboardFragment
import com.app.androidinitialproject.ui.home.base.HomeBaseFragment
import com.app.androidinitialproject.utility.Utils
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : HomeBaseFragment<LoginViewModel, FragmentLoginBinding>() {
    companion object {
        fun newInstance() = LoginFragment()

        private const val RC_SIGN_IN = 9001

    }

    override fun getToolbarMenuHandler(): ToolbarMenuHandler? = null
    override fun getLayoutResource(): Int = R.layout.fragment_login
    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java
    private lateinit var mAuth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

   // private lateinit var mCallbackManager: CallbackManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
      //  initFB()

        setUpFcm()


        viewBinding.editPassword.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_GO) {
                viewModel.login()
                true
            }
            false
        }
        /* viewBinding.imageStoreUsername.setOnClickListener {
             try {
                 val tag = it.tag.toString().toInt()
                 if (tag == 0) {
                     it.tag = "1"
                     viewBinding.imageStoreUsername.setImageResource(R.drawable.on)
                     viewModel.saveUser = true
                 } else {
                     it.tag = "0"
                     viewBinding.imageStoreUsername.setImageResource(R.drawable.off)
                     viewModel.saveUser = false
                 }
             } catch (e: Exception) {
                 e.printStackTrace()
             }
         }*/

        //viewBinding.textUuid.setText(AppManager.DATABASE_NAME)
        setSavedLoginEmailAndPassword()

        socialLogin()
    }

//    private fun initFB() {
//        mCallbackManager = CallbackManager.Factory.create()
//        viewBinding.buttonLoginFacebookNative.fragment = this
//        viewBinding.buttonLoginFacebookNative.loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK
//        viewBinding.buttonLoginFacebookNative.setPermissions("email", "public_profile")
//        viewBinding.buttonLoginFacebookNative.registerCallback(
//            mCallbackManager,
//            object : FacebookCallback<LoginResult> {
//                override fun onSuccess(loginResult: LoginResult) {
//                    handleFacebookAccessToken(loginResult.accessToken)
//                }
//
//                override fun onCancel() {
//                }
//
//                override fun onError(error: FacebookException) {
//                    showToast("Sorry! Failed")
//                }
//            })
//    }


//    private fun handleFacebookAccessToken(token: AccessToken) {
//
//        showProgressDialog()
//
//        val credential = FacebookAuthProvider.getCredential(token.token)
//        mAuth.signInWithCredential(credential)
//            .addOnCompleteListener(context as Activity) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    val user = mAuth.currentUser
//                    showToast(""+user?.displayName)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    showToast("" + task.exception)
//
//
//                }
//
//
//                dismissProgressDialog()
//
//            }
//    }



    private fun setUpFcm() {


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context as Activity, gso)


        mAuth = FirebaseAuth.getInstance()
    }

    private fun socialLogin() {

        viewBinding.buttonFacebook.setOnClickListener {

           // facebookClicked()

        }

        viewBinding.buttonGoogle.setOnClickListener {

            googleClicked()
        }
    }
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        showProgressDialog()

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        activity?.let {
            mAuth.signInWithCredential(credential)
                .addOnCompleteListener(context as Activity) { task ->
                    viewModel.showToast(""+task.isSuccessful+"")
                    if (task.isSuccessful) {

                        val user = mAuth.currentUser
                        showToast(""+user?.displayName)

                        signOutFirebase()
                    } else {

                        showToast("Sorry, Failed")
                    }

                    dismissProgressDialog()
                }.addOnFailureListener(it, OnFailureListener {
                    it.message?.let { it1 -> viewModel.showToast(it1) }
                })
        }
    }



    private fun signOutFirebase() {


        FirebaseAuth.getInstance().signOut()
//        LoginManager.getInstance().logOut()



        googleSignInClient?.signOut()




    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                //  showAlert("" + e.printStackTrace().toString())
            }
        } else {
           // mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    private fun googleClicked() {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun facebookClicked() {

//        LoginManager.getInstance().logOut()
//        viewBinding.buttonLoginFacebookNative.performClick()
    }

    fun setSavedLoginEmailAndPassword() {
        val username = AppManager.getSavedLoginEmail()
        val password = AppManager.getSavedLoginPassword()

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            //viewBinding.imageStoreUsername.performClick()
            viewBinding.editEmail.setText(username)
            viewBinding.editPassword.setText(password)
        }
    }

    private fun observeLiveData() {
        viewModel.copyId.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.getContentIfNotHandled()?.let {
                //copyUUID()
            }
        })
        viewModel.onSaveUserClicked.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.getContentIfNotHandled()?.let {
                // viewBinding.imageStoreUsername.performClick()
            }
        })

        viewModel.onLoginSuccessful.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                fragmentListener?.navigateToWebViewFragment()
               // replaceFragment(DashboardFragment(),true,false)
            }
        })
    }


    override fun showInputError(inputError: InputError) {
        when (inputError.errorType) {
            InputErrorType.EMAIL -> {
                viewBinding.editEmail.requestFocus()
                showMessageDialog(inputError.message)
            }
            InputErrorType.PASSWORD -> {
                viewBinding.editPassword.requestFocus()
                showMessageDialog(inputError.message)
            }
        }
    }
}