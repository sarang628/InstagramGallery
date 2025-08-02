package com.example.instagramgallery.di.gallery

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.sarang.instagralleryModule.compose.GalleryListWithPreviewScreen
import com.sarang.instagralleryModule.compose.component.AskPermission
import com.sarang.instagralleryModule.viewmodel.GalleryViewModel
import com.sryang.library.compose.workflow.BestPracticeViewModel
import com.sryang.library.compose.workflow.DescribePermissionDialog
import com.sryang.library.compose.workflow.MoveSystemSettingDialog
import com.sryang.library.compose.workflow.PermissonWorkFlow.CheckRationale
import com.sryang.library.compose.workflow.PermissonWorkFlow.DeniedPermission
import com.sryang.library.compose.workflow.PermissonWorkFlow.GrantedPermission
import com.sryang.library.compose.workflow.PermissonWorkFlow.InitialPermissionCheck
import com.sryang.library.compose.workflow.PermissonWorkFlow.RecognizeToUser
import com.sryang.library.compose.workflow.PermissonWorkFlow.RequestPermission
import com.sryang.library.compose.workflow.PermissonWorkFlow.ShowRationale
import com.sryang.library.compose.workflow.PermissonWorkFlow.SuggestSystemSetting
import com.sryang.library.compose.workflow.RationaleDialog

/**
 * viewModel을 상위 컴포넌트에서 선안하지 않으면 권한체크 viewModel.state가 계속 초기화 되어 로직이 정상적으로 동작 안함.
 */
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GalleryWithPermission(
    viewModel: BestPracticeViewModel = BestPracticeViewModel(),
    permission : String = Manifest.permission.READ_MEDIA_IMAGES,
    galleryViewModel : GalleryViewModel = hiltViewModel()
) {
    var timeDiff : Long by remember { mutableStateOf(0L) } // 영구 권한 거부 상태 체크를 위한 시간
    val requestPermission = rememberPermissionState(permission, { viewModel.permissionResult(it, System.currentTimeMillis() - timeDiff); })
    val state = viewModel.state
    var stateTxt by remember { mutableStateOf("RequestPermission") }

    var uiState = galleryViewModel.uiState

    when (state) {
        InitialPermissionCheck  /* 최초 권한 체크 */ -> { viewModel.initialPermissionCheck(requestPermission.status.isGranted) }
        RecognizeToUser         /* UX에 권한을 필요로 하는 정보 인지 시키기 */-> { DescribePermissionDialog(onYes = { viewModel.yesInRecognizeUser() }, onNo = { viewModel.noInRecognizeUser() }) }
        CheckRationale          /* rational 여부 확인 */-> { viewModel.checkRational(requestPermission.status.shouldShowRationale) }
        DeniedPermission        /* 권한 거부 */-> { stateTxt = "권한을 거부함."; AskPermission(onRequestPermission = {viewModel.request()})}
        GrantedPermission       /* 사용자가 권한을 허가했다면, 자원 접근 가능 */-> { stateTxt = "권한을 허용함."; GalleryListWithPreviewScreen(list = uiState.list, folderList = uiState.folderList) }
        RequestPermission       /* 런타임 권한 요청하기 */ -> { LaunchedEffect(state == RequestPermission) { requestPermission.launchPermissionRequest(); timeDiff = System.currentTimeMillis() } }
        SuggestSystemSetting    /* 권한 거부 상태에서 요청 시 */ -> { MoveSystemSettingDialog(onMove = { viewModel.onMoveInSystemDialog() }, onDeny = {viewModel.noInSystemDialog()}) }
        ShowRationale           /* rationale을 표시 */ -> { RationaleDialog({ viewModel.yesRationale() }, {viewModel.noRationale()}) }
    }

    Column {
        Text(state.toString().split("$")[1].split("@")[0])
    }
}