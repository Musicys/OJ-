<template>
    <div class="footer">
        <div class="footer-content">
            <div class="links">
                <a href="/about">关于我们</a>
                <a href="/help">帮助中心</a>
                <a href="/privacy">隐私政策</a>
                <a href="/terms">服务条款</a>
            </div>
            <div class="social-media">
                <a href="https://github.com" target="_blank">
                    <!-- <img src="@/assets/github.svg" alt="GitHub"> -->
                </a>
                <a href="https://twitter.com" target="_blank">
                    <!-- <img src="@/assets/twitter.svg" alt="Twitter"> -->
                </a>
                <a href="https://facebook.com" target="_blank">
                    <!-- <img src="@/assets/facebook.svg" alt="Facebook"> -->
                </a>
            </div>
            <div class="copyright">
                © 2024 Lioj. All rights reserved.
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.footer {
    background: var(--color-menu-light-bg);
    text-align: center;
    font-size: 1em;
    border-top: 1px solid rgb(92, 88, 88);
    padding: 2em 0;
    height: 100%;
}

.footer-content {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    gap: 1em;
}

.links {
    display: flex;
    justify-content: center;
    gap: 2em;
}

.links a {
    color: #9F9F9F;
    text-decoration: none;
}

.links a:hover {
    color: #00aeec;
}

.social-media {
    display: flex;
    justify-content: center;
    gap: 1em;
}

.social-media img {
    width: 24px;
    height: 24px;
}

.copyright {
    color: #6E6E6E;
}
</style>