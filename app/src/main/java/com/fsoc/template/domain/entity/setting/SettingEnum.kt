package com.fsoc.template.domain.entity.setting

const val FIRST_SETTING = "FIRST_SETTING"
const val REWARD_SETTING = "REWARD_SETTING"
const val UNIT_SETTING = "UNIT_SETTING"
const val ROUND_SETTING = "ROUND_SETTING"
const val CHARACTER_SETTING = "CHARACTER_SETTING"
const val TIME_SETTING = "TIME_SETTING"
const val MESSAGE_SETTING = "MESSAGE_SETTING"
const val REPORT_SETTING = "REPORT_SETTING"
const val SORT_SETTING = "SORT_SETTING"
const val PAY_BONUS_SETTING = "PAY_BONUS_SETTING"
const val ERR_SETTING = "ERR_SETTING"
const val DETACHED_SETTING = "DETACHED_SETTING"
const val MINOR_REPORT_SETTING = "MINOR_REPORT_SETTING"

enum class RewardSettingEnum(val value: String) {
    NO_REWARD("0 trả thưởng"),
    REWARD_ONE("Nhân 1 lần"),
    REWARD_TWO("Nhân 2 lần"),
    REWARD_THREE("Nhân 3 lần"),
    REWARD_FOUR("Nhân 4 lần"),
    REWARD_FIVE("Nhân 5 lần"),
    REWARD_SIX("Nhân 6 lần"),
    REWARD_SEVEN("Nhân 7 lần"),
    REWARD_EIGHT("Nhân 8 lần"),
    REWARD_NINE("Nhân 9 lần"),
    REWARD_TEN("Nhân 10 lần"),
}

enum class UnitSettingEnum(val value: String) {
    MONEY_TRANSFER("1. Chuyển theo tiền"),
    POINT_TRANSFER("2. Chuyển theo điểm"),
}

enum class RoundSettingEnum(val value: String) {
    NO_ROUND("1. Không làm tròn"),
    ROUND_ONE("2. Làm tròn đến 10"),
    ROUND_TWO("3. Làm tròn đến 50"),
    ROUND_THREE("4. Làm tròn đến 100"),
}

enum class CharacterSettingEnum(val value: String) {
    NO_CHARACTER("1. Không giới hạn"),
    ROUND_ONE("2. 160 kí tự"),
    ROUND_TWO("3. 320 kí tự"),
    ROUND_THREE("4. 480 kí tự"),
    ROUND_FOUR("5. 1000 kí tự"),
    ROUND_FIVE("6. 2000 kí tự (Zalo)"),
}

enum class TimeSettingEnum(val value: String) {
    NO_NOTIFICATION("1. Không nhắn hết giờ"),
    NOTIFICATION("2. Nhắn hết giờ"),
}

enum class MessageSettingEnum(val value: String) {
    SAME_MESSAGE("1. Có nhận tin trùng"),
    NO_SAME_MESSAGE("2. Không nhận tin trùng"),
}

enum class ReportSettingEnum(val value: String) {
    REPORT_OLD("1. Báo cáo kiểu cũ"),
    REPORT_NEW("2. Báo cáo kiểu mới"),
}

enum class SortSettingEnum(val value: String) {
    SORT_ONE("1. Theo tổng tiền nhận"),
    SORT_TWO("2. Theo tổng tiền tồn"),
}

enum class PayBonusSettingEnum(val value: String) {
    PAY_BONUS_ONE("1. Trả đủ"),
    PAY_BONUS_TWO("2. Trả nhiều nhất 2 nháy"),
    PAY_BONUS_THREE("3. Trả nhiều nhất 3 nháy"),
    PAY_BONUS_FOUR("4. Trả nhiều nhất 4 nháy"),
}

enum class ErrSettingEnum(val value: String) {
    ERR_ONE("1. Không cảnh báo"),
    ERR_TWO("2. Có cảnh báo"),
}

enum class DetachedSettingEnum(val value: String) {
    NO_DETACHED("1. Không tách xiên"),
    DETACHED("2. Tách riêng xiên 2-3-4"),
}

enum class MinorReportSettingEnum(val value: String) {
    MINOR_REPORT_ONE("1. Không báo thiếu tin "),
    MINOR_REPORT_TWO("2. Có báo thiếu tin "),
}
