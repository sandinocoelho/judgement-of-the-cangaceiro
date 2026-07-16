# Release signing (Android) — CANGA-28

The fork signs release APKs with a locally-held keystore (no Play App Signing).
**Losing the keystore or its password permanently ends the ability to update
installed copies of the app.** Treat backups and the restore drill as
first-class release infrastructure, not chores.

## The key

- File: `judgement-release.jks` — RSA 4096, SHA384withRSA, validity 10.000 days
  (generated 2026-07-16, expires ~2053).
- Alias: `judgement`.
- Lives **outside the repository**. The repo's `.gitignore` blocks
  `*.jks`, `*.keystore`, and `keystore.properties` as a second line of defense.
- Passwords live in the password manager only.

Generation command used (for reference / future re-issue of a *different* key):

```powershell
keytool -genkeypair -v `
  -keystore judgement-release.jks `
  -alias judgement `
  -keyalg RSA -keysize 4096 `
  -validity 10000
```

## Backups (acceptance criteria: 2+ verified, offline/encrypted)

1. Keep at least **two** copies besides the working one, e.g. encrypted USB
   drive + encrypted cloud vault, in distinct physical locations.
2. **Verify every copy** after writing it:
   `keytool -list -v -keystore <backup-path>` must open with the password and
   show the expected SHA-256 fingerprint.
3. Record the SHA-256 fingerprint in the password manager next to the
   passwords — it is how any copy is confirmed to be the real key.

## Restore drill (do this once now, and after any backup rotation)

1. Copy a backup to a scratch directory (ideally another machine).
2. `keytool -list -v -keystore <copy>` — opens with the password manager's
   password, fingerprint matches the recorded one.
3. Point a scratch `keystore.properties` at the copy and run
   `gradlew :android:assembleRelease` — build must produce a signed APK.

## Local build wiring

1. Copy `keystore.properties.template` (repo root) to `keystore.properties`.
2. Fill in the path and the passwords from the password manager.
3. `gradlew :android:assembleRelease` now produces
   `android/build/outputs/apk/release/android-release.apk` (signed).
   Without `keystore.properties` the same task still builds, but unsigned
   (`android-release-unsigned.apk`) — this keeps CI's debug pipeline and other
   machines working with no keystore present.

Verify a signed APK with:

```powershell
& "$env:LOCALAPPDATA/Android/Sdk/build-tools/36.1.0/apksigner.bat" verify --print-certs android/build/outputs/apk/release/android-release.apk
```

## CI wiring (consumed by the E9 release pipeline)

- Upload `judgement-release.jks` to GitLab **Settings → CI/CD → Secure Files**.
- Store `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD` as **masked +
  protected** CI variables (protected = only available on protected
  branches/tags, which is what release tags are).
- The release job downloads the Secure File and writes `keystore.properties`
  at runtime; nothing sensitive is ever echoed to the job log.
