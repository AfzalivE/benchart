package page.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import components.ChartUi
import components.FormUi
import components.Heading
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

@Composable
fun HomePage(
    viewModel: HomeViewModel = remember { HomeViewModel() }
) {
    Div(
        attrs = {
            classes("container-fluid")
        }
    ) {

        // Heading
        Heading()

        // Error
        if (viewModel.errorMsg.isNotBlank()) {
            Error(viewModel.errorMsg)
        }


        Div(attrs = {
            classes("row")
            style {
                padding(40.px)
            }
        }) {
            Div(attrs = {
                classes("col-md-4")
            }) {
                FormUi(
                    onFormChanged = { form ->
                        viewModel.onAutoFormChanged(form)
                    },
                    testNames = viewModel.testNames,
                    onTestNameChanged = { newTestName ->
                        viewModel.onTestNameChanged(newTestName)
                    }
                )
            }

            val hasOverrunMs = viewModel.charts?.frameOverrunChart?.dataSets?.isNotEmpty() ?: false

            Div(attrs = {
                classes(
                    if (hasOverrunMs) {
                        "col-md-4"
                    } else {
                        "col-md-8"
                    }
                )
            }) {
                viewModel.charts?.let { charts ->
                    // Rendering frameDurationMs
                    charts.frameDurationChart.dataSets.isNotEmpty().let { hasData ->
                        if (hasData) {
                            ChartUi(charts.frameDurationChart)
                        }
                    }
                }
            }

            if (hasOverrunMs) {
                Div(attrs = {
                    classes(
                        "col-md-4"
                    )
                    style {
                        position(Position.Sticky)
                        top(0.px)
                    }
                }) {
                    viewModel.charts?.frameOverrunChart?.let { frameOverrunChart ->
                        ChartUi(frameOverrunChart)
                    }
                }
            }
        }


    }
}