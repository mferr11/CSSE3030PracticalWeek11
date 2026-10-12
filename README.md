# CSSE3030 Week 11 Practical: Security Testing and Fuzzing

You write Jazzer **fuzz targets** (JUnit-style methods that Jazzer calls over and over with
generated input), read what Jazzer reports, fix the faults it finds, and keep each finding
as a JUnit regression test.
[Jazzer](https://github.com/CodeIntelligenceTesting/jazzer) is a coverage-guided fuzzer
for Java that runs as JUnit tests.

| Part | Content | Code under test |
|---|---|---|
| 1 | Concepts and strategy (paper) | none |
| 2 | Grammars (paper) | none |
| 3 | Your first fuzz target | `Words`, `QuantityParser` |
| 4 | Fuzzing bigger programs | `UrlCodec`, `TicketParser` |
| 5 | Jazzer's SQL injection detector | `StudentDao` (in-memory H2 database) |

Each class has its **contract in its Javadoc**. Work from the contract, not from the
source: your fuzz targets and tests should say what the code *should* do. Every fuzz target
and test you write already exists as a skeleton with a `TODO` comment, under
`src/test/java/csse3030/week11/`. The worksheet names the TODO for each question.

## Requirements

- JDK 17 or newer (`java -version`)
- Nothing else to install: use the Gradle wrapper (`./gradlew`) included in the repo
- Internet access to Maven Central the first time you build (Gradle downloads JUnit, Jazzer and H2)

## Get the code

```bash
git clone https://github.com/mferr11/CSSE3030PracticalWeek11.git
cd CSSE3030PracticalWeek11
./gradlew --version
```

- **Mac or Linux:** if you see `Permission denied`, run `chmod +x ./gradlew` once.
- **Windows:** use PowerShell and write `.\gradlew` instead of `./gradlew`. If any part of the
  path to the project contains a space (most commonly your username), the build can fail, so
  clone into a space-free folder such as `C:\src`. If you see `Filename too long` while
  cloning, use a short path or run `git config --global core.longpaths true`.

## Running

| Command | Part | What it does |
|---|---|---|
| `./gradlew fuzzFirstWord` | 3 | Fuzzes `FirstWordFuzzTest` (about 30 s) |
| `./gradlew fuzzQuantity` | 3 | Fuzzes `QuantityFuzzTest` (about 30 s) |
| `./gradlew fuzzUrlCodec` | 4 | Fuzzes `UrlCodecFuzzTest` (about 60 s) |
| `./gradlew fuzzTicket` | 4 | Fuzzes `TicketParserFuzzTest` (about 60 s) |
| `./gradlew fuzzStudentDao` | 5 | Fuzzes `StudentDaoFuzzTest` (about 60 s) |
| `./gradlew test` | any | Runs every JUnit test, and replays each fuzz target **once** without fuzzing (seconds) |

A `fuzz...` task stops at the first finding or when the target's `maxDuration` is up, and
always runs again when you re-run it.

### What a pass and a finding look like

- **Pass:** `BUILD SUCCESSFUL`. A fuzz target that ran for its full time without finding
  anything also passes (look for `Done ... runs in 30 second(s)`).
- **Finding:** the task fails with `BUILD FAILED`, and the target shows `FAILED` with the
  exception Jazzer found, followed by a stack trace that points into the code under test.
  Jazzer prints the failing input just above the failure and saves it in a file named
  `crash-<hash>` in the project folder. Open it with `cat` (`type` on Windows). An empty
  file means the failing input was the empty string.
- The full report for a run is in `build/reports/tests/<task>/index.html`.

Each fuzz target is annotated `@FuzzTest(maxDuration = ...)`. Without `maxDuration`, Jazzer
would fuzz for 5 minutes, so the Gradle fuzz tasks give up after 3 minutes with
`Timeout has been exceeded`. If you see that, check your `@FuzzTest` annotation.

A `crash-...` file is not committed (see `.gitignore`) and is not replayed by
`./gradlew test`. Keep a finding by turning it into a JUnit regression test instead.
Jazzer's scratch corpus in `.cifuzz-corpus/` is safe to delete at any time.

## If you fall behind

If you did not finish a Part, you can load the fixed source for the earlier Parts and carry
on. Run **one** of these from the project folder:

```bash
git checkout checkpoint-after-part3 -- src/main   # Words fixed (end of Part 3)
git checkout checkpoint-after-part4 -- src/main   # Words, UrlCodec and TicketParser fixed (end of Part 4)
```

Each command replaces only the files under `src/main` with the versions from that
checkpoint. Your tests under `src/test` are not touched. (Run `git diff src/main` to see
what changed; `git checkout main -- src/main` puts back the original code.)

## Getting unstuck

| Problem | Try |
|---|---|
| `BUILD FAILED` before any test runs | Read the first error. A missing `;` or class name is a compile error in your file |
| Not sure what you changed | `git status` and `git diff` |
| Want to undo your edits to one file | `git checkout -- path/to/File.java` (this discards your edits to it) |
| Gradle behaves strangely | `./gradlew clean`, then run the task again |
| Download errors on first run | Check you have internet access to Maven Central and try again |
| A fuzz run seems stuck | Wait: it stops after its time limit (3 minutes at most). Press Ctrl+C if you must |
| Still stuck | Ask your tutor |
