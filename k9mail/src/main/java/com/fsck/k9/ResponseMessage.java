package com.fsck.k9;

public class ResponseMessage {

        protected String text;
        protected boolean isMe;

        public ResponseMessage(String text, boolean isMe) {
            this.text = text;
            this.isMe = isMe;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isMe() {
            return isMe;
        }

        public void setMe(boolean me) {
            isMe = me;
        }
    }

