package com.appb.app.appb.api;

import com.appb.app.appb.data.DvachError;
import com.appb.app.appb.utils.Utils;

import java.io.IOException;

/**
 * Created by seishu on 27.02.2018.
 */

public class DownloadErrorException extends IOException {

    DvachError dvachError;

    public DownloadErrorException(DvachError dvachError) {
        this.dvachError = dvachError;
    }

    @Override
    public String getMessage() {
        return Utils.getErrorMessage(dvachError);
    }
}
