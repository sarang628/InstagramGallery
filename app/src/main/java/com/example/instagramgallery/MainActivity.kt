package com.example.instagramgallery

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.sarang.instagralleryModule.compose.BottomSendList
import com.sarang.instagralleryModule.compose.GalleryBottomSheet
import com.sarang.instagralleryModule.compose.GalleryNavHost
import com.sarang.instagralleryModule.compose.PreviewGalleryNavHost
import com.sarang.instagralleryModule.compose.component.AskPermission
import com.sarang.torang.compose.bottomsheet.PickHeight70PercentBottomSheetScaffold
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
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "init"){
                        composable("init"){
                            Column {
                                Button({navController.navigate("GalleryNavHost")}) {
                                    Text("GalleryNavHost")
                                }
                                Button({navController.navigate("GalleryBottonSheetTest")}) {
                                    Text("GalleryBottonSheetTest")
                                }
                            }
                        }
                        composable("GalleryNavHost"){
                            WorkFlowImpl()
                        }
                        composable("GalleryBottonSheetTest"){
                            GalleryBottonSheetTest()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WorkFlowImpl(
    viewModel: BestPracticeViewModel = BestPracticeViewModel(),
    permission : String = Manifest.permission.READ_MEDIA_IMAGES
) {
    var timeDiff : Long by remember { mutableStateOf(0L) } // 영구 권한 거부 상태 체크를 위한 시간
    val requestPermission = rememberPermissionState(permission, { viewModel.permissionResult(it, System.currentTimeMillis() - timeDiff); })
    val state = viewModel.state
    var stateTxt by remember { mutableStateOf("RequestPermission") }

    when (state) {
        InitialPermissionCheck  /* 최초 권한 체크 */ -> { viewModel.initialPermissionCheck(requestPermission.status.isGranted) }
        RecognizeToUser         /* UX에 권한을 필요로 하는 정보 인지 시키기 */-> { DescribePermissionDialog(onYes = { viewModel.yesInRecognizeUser() }, onNo = { viewModel.noInRecognizeUser() }) }
        CheckRationale          /* rational 여부 확인 */-> { viewModel.checkRational(requestPermission.status.shouldShowRationale) }
        DeniedPermission        /* 권한 거부 */-> { stateTxt = "권한을 거부함."; AskPermission(onRequestPermission = {requestPermission.launchPermissionRequest()})}
        GrantedPermission       /* 사용자가 권한을 허가했다면, 자원 접근 가능 */-> { stateTxt = "권한을 허용함." }
        RequestPermission       /* 런타임 권한 요청하기 */ -> { LaunchedEffect(state == RequestPermission) { requestPermission.launchPermissionRequest(); timeDiff = System.currentTimeMillis() } }
        SuggestSystemSetting    /* 권한 거부 상태에서 요청 시 */ -> { MoveSystemSettingDialog(onMove = { viewModel.onMoveInSystemDialog() }, onDeny = {viewModel.noInSystemDialog()}) }
        ShowRationale           /* rationale을 표시 */ -> { RationaleDialog({ viewModel.yesRationale() }, {viewModel.noRationale()}) }
    }

    Column {
        Text(state.toString().split("$")[1].split("@")[0])
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun test() {
    PickHeight70PercentBottomSheetScaffold(show = true, onHidden = { /*TODO*/ }, imageSelectCompose = {
        PreviewGalleryNavHost()
    }) {

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEEEEEE)
@Composable
fun PreviewBottomSendList() {
    BottomSendList(selectedList = listOf(""), onSend = {})
}

@Composable
fun GalleryBottonSheetTest(){
    var show by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize())
    {
        GalleryBottomSheet(
            imageSelectBottomSheetScaffold = { show, onHidden, imageSelectCompose, content ->
                PickHeight70PercentBottomSheetScaffold(show = show, onHidden = onHidden, imageSelectCompose = imageSelectCompose, content = content)
            },
            content = { Box(modifier = Modifier.fillMaxSize()) { Button(onClick = { show = true }) { Text(text = "show") } } },
            show = show,
            onHidden = { show = false }
        )
    }
}