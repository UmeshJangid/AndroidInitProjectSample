package com.app.androidinitialproject.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.androidinitialproject.R
import com.app.androidinitialproject.databinding.FragmentProfileBinding
import com.app.androidinitialproject.ui.home.base.HomeBaseFragment
import com.app.androidinitialproject.utility.showMessageDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.util.*


class ProfileFragment : HomeBaseFragment<ProfileViewModel, FragmentProfileBinding>() {
    companion object {
        fun newInstance() = ProfileFragment()
    }

    private var mCropImageUri: Uri? = null
    override fun getToolbarMenuHandler(): ToolbarMenuHandler? = null
    override fun getLayoutResource(): Int = R.layout.fragment_profile
    override fun getViewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java
    // override fun getViewModelBindingId(): Int = BR.viewmodel

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            invalidateUpdateButtonVisibility(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewActions = object : ViewActions {
            /* Set your object values this function */
            /*  override fun setProfileDetails(profileDetails: User) {
                  profileDetails.let {
                      viewBinding.image.imageUrl = it.profile_photo_full_path
                      viewBinding.editFirstName.setText(it.first_name)
                      viewBinding.editLastName.setText(it.last_name)
                      viewBinding.editEmail.setText(it.email)
                      viewBinding.editPhone.setText(it.mobile)
                      viewBinding.editFirstName.addTextChangedListener(textWatcher)
                      viewBinding.editLastName.addTextChangedListener(textWatcher)
                      viewBinding.editLastName.addTextChangedListener(textWatcher)
                  }
              }
  */
            override fun onUserProfileUpdated(message: String) {
                showToast(message)
                invalidateUpdateButtonVisibility(false)
            }
        }

        /*    if (!AppManager.isUserLoggedIn()) {
                viewBinding.constLogin.visibility = View.VISIBLE
                viewBinding.buttonLogin.setOnClickListener {
                    getFragmentListener()?.navigateToLoginFragment()
                }
                viewBinding.buttonSignUp.setOnClickListener {
                    getFragmentListener()?.navigateToRegisterFragment()
                }
            } else {
                setHeaderPNGTint()
                invalidateWalletButton()
                viewModel.getUserProfileDetails()
            }*/

        viewBinding.tvEdit.setOnClickListener {
            /*Pick  image when It is get clicked .*/
            pickImage()
        }
    }

    private fun setHeaderPNGTint() {
        context?.let {
            viewBinding.image0.setColorFilter(
                ContextCompat.getColor(
                    it,
                    R.color.colorAccent
                )
            )
        }
    }

    fun invalidateUpdateButtonVisibility(show: Boolean) {
        if (show) viewBinding.buttonUpdate.visibility = View.VISIBLE
        else viewBinding.buttonUpdate.visibility = View.GONE
    }

    interface ViewActions {
        //  fun setProfileDetails(profileDetails: ProfileDetails)
        fun onUserProfileUpdated(message: String)
    }


    fun pickImage() {
        if (CropImage.isExplicitCameraPermissionRequired(requireContext())) {

            val permissionlistener: PermissionListener = object : PermissionListener {
                override fun onPermissionGranted() {
                    CropImage.startPickImageActivity(activity!!)
                }

                override fun onPermissionDenied(deniedPermissions: List<String>) {
                    showToast("Permission Denied\n$deniedPermissions")
                }
            }

            TedPermission.with(context)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("CAMERA and STORAGE permission is required for this functionality.")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();



        } else CropImage.startPickImageActivity(requireActivity())
    }

    /**
     * Start crop image activity.
     *
     * @param uri the uri
     */
    fun startCropImageActivity(uri: Uri?) {
        Handler().postDelayed({
            if (uri != null) CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.OFF)
                .setAllowFlipping(false)
                .setCropMenuCropButtonTitle("")
                .setAspectRatio(1, 1) //                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setInitialCropWindowPaddingRatio(0f)
                .setCropMenuCropButtonIcon(R.drawable.ic_done_white_24dp)
                .setScaleType(CropImageView.ScaleType.CENTER_CROP)
                .start(requireActivity())
        }, 500)
    }

    /**
     * Sets user image.
     *
     * @param imageUri the image uri
     */
    private fun setUserImage(imageUri: Uri?) {
        if (imageUri != null) {
            Glide.with(requireContext())
                .setDefaultRequestOptions(
                    RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                )
                .load(imageUri)
                .into(viewBinding.image)
            /* PicassoTrustAll.getInstance(context).load(imageUri).placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                    .fit()
                    .centerCrop()
                    .into(binding.imgHeathTip);*/
        }
    }


    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            val imageUri = CropImage.getPickImageResultUri(requireContext(), data)
            try {
                if (getFileExtension(imageUri.toString()).equals("png",true)) {
                    showMessageDialog("PNG images are not allowed, please try other image formate")
                    return
                }
            } catch (e: Exception) { }
            if (CropImage.isReadExternalStoragePermissionsRequired(requireContext(), imageUri)) {
                mCropImageUri = imageUri
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE)
            } else startCropImageActivity(imageUri)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                if (result.uri != null) {
                    mCropImageUri = result.uri
                    setUserImage(mCropImageUri)
                    // File file = new File(FileUtils.getPath(context, mCropImageUri));
//                    viewModel.uploadFile(new NetworkCall(context), "profile_image", file);
                }
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            showMessageDialog("Cropping failed: Please retry !!")
        }
    }


}




